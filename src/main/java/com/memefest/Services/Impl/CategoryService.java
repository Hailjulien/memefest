package com.memefest.Services.Impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import com.memefest.DataAccess.Category;
import com.memefest.DataAccess.CategoryFollower;
import com.memefest.DataAccess.MainCategory;
import com.memefest.DataAccess.SubCategory;
import com.memefest.DataAccess.User;
import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.DataAccess.JSON.SubCategoryJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.CategoryOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Services.UserOperations;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.Query;

@Stateless(name = "CategoryService")
public class CategoryService implements CategoryOperations{

  @PersistenceContext(unitName = "memeFest", type = PersistenceContextType.TRANSACTION)
  private EntityManager entityManager;

  @EJB
  private TopicOperations topicOps;

  @EJB
  private UserOperations userOperations;
    
  public void createCategory(CategoryJSON category) {
    if (category == null)
      return; 
    if (getCategoryEntity(category) != null)
      return; 
    Category newCategory = new Category();
    newCategory.setCat_Name(category.getCategoryName());
    this.entityManager.persist(newCategory);
    createCategoryFollowers(category);
    for (TopicJSON topic : category.getTopics())
      topicOps.createTopic(topic);
 
    }
      
  public Set<CategoryJSON> searchCategory(CategoryJSON category){
    if(category !=  null && category.getCategoryName() != null){
      Query query = entityManager.createNamedQuery("Category.searchByName");
      query.setParameter("categoryName", category.getCategoryName());
      return (Set<CategoryJSON>) query.getResultList();
    } 
    return null;
  }
    
  public void editCategory(CategoryJSON category) {
    if (category == null)
      return; 
    if (category.isCancelled()) {
      removeCategory(category);
      return;
    } 
    Category foundCategory = getCategoryEntity(category);
    if (category.getCategoryName() != null)
      foundCategory.setCat_Name(category.getCategoryName()); 
    if (category.getCategoryId() != 0)
      foundCategory.setCat_Id(category.getCategoryId()); 
    for (TopicJSON topic : category.getTopics())
      topicOps.editTopic(topic); 
    removeCategoryFollowers(category);
    createCategoryFollowers(category);
  }
      
  public void createCategoryFollowers(CategoryJSON category) {
    Category foundCategory = getCategoryEntity(category);
    if (foundCategory != null)
      return;
    Set<CategoryFollower> catFollowers = getCategoryFollowers(category);
    Set<User> catUsers = (Set<User>)catFollowers.stream().map(catFollower -> catFollower.getUser()).collect(Collectors.toSet());
    for (UserJSON user : category.getFollowedBy()) {
      User follower = this.userOperations.getUserEntity(user);
      if (follower == null)
        return;
      if (!catUsers.contains(follower)) {
        CategoryFollower newCatFollower = new CategoryFollower();
        newCatFollower.setCat_Id(foundCategory.getCat_Id());
        newCatFollower.setUserId(follower.getUserId());
        this.entityManager.persist(newCatFollower);
      } 
    } 
  }
      
  public Set<CategoryFollower> getCategoryFollowers(CategoryJSON category) {
    Category foundCategory = getCategoryEntity(category);
    if (foundCategory == null);
    Query query = this.entityManager.createNamedQuery("CategoryFollower.findByCategoryId");
    query.setParameter("categoryId", Integer.valueOf(foundCategory.getCat_Id()));
    Set<CategoryFollower> results = (Set<CategoryFollower>)query.getResultStream().map(object -> (CategoryFollower)object).collect(Collectors.toSet());
    return results;
  }
      
  public void removeCategoryFollowers(CategoryJSON category) {
    Set<CategoryFollower> categoryFollowers = getCategoryFollowers(category);
    for (Iterator<UserJSON> iterator = category.getCancelFollowedBy().iterator(); iterator.hasNext(); ) {
      UserJSON user = iterator.next();
      categoryFollowers.stream().filter(candidate -> {
          if (candidate.getUserId() == user.getUserId())
              return true; 
          User userEntity = this.userOperations.getUserEntity(user);
          return (userEntity == candidate.getUser());
        }).forEach(candidate -> this.entityManager.remove(candidate));
    } 
  }

      
  public Set<UserJSON> getCategoryFollowersInfo(CategoryJSON category){
    Set<UserJSON> followers = getCategoryFollowers(category).stream().map(categFollowerEntity ->{
      UserJSON user = new UserJSON(categFollowerEntity.getUserId(), categFollowerEntity.getUser().getUsername());
      return user;
    }).collect(Collectors.toSet());
    return followers;
  }
      
  public Category getCategoryEntity(CategoryJSON category) throws NoResultException {
    Category foundCategory = null;
    if ((category != null) && category.getCategoryId() != 0 && category.getCategoryId() != 1) {
          foundCategory = (Category)this.entityManager.find(Category.class, Integer.valueOf(category.getCategoryId()));
          return foundCategory;
    } 
    Query query = this.entityManager.createNamedQuery("Category.getCategoryByTitle");
    query.setParameter("title", category.getCategoryName());
    foundCategory = (Category)query.getSingleResult();
    return foundCategory;
  }
      
  public MainCategory getMainCategoryEntity(CategoryJSON category) throws NoResultException {
    MainCategory foundCategory = null;
    if ((category != null) && category.getCategoryId() != 0 && category.getCategoryId() != 1) {
      foundCategory = (MainCategory)this.entityManager.find(MainCategory.class, Integer.valueOf(category.getCategoryId()));
      return foundCategory;
    } 
    Query query = this.entityManager.createNamedQuery("MainCategory.getCategoryByTitle");
    query.setParameter("title", category.getCategoryName());
    foundCategory = (MainCategory)query.getSingleResult();
    return foundCategory;
  }
    
    
      
  public void createSubCategory(SubCategoryJSON category) throws IllegalArgumentException {
    if (category == null || (category.getCategoryName() == null && category.getCategoryId() == 0))
      return; 
    SubCategory subCategory = null;
    try {
      subCategory = getSubCategoryEntity((CategoryJSON)category);
      if (subCategory != null)
        return; 
    } catch (NoResultException ex) {
      subCategory = new SubCategory();
      subCategory.setCat_Name(category.getCategoryName());
      if (category.getParentCategories() == null || category.getParentCategories().size() == 0) {
        Set<CategoryJSON> mainCatList = (Set<CategoryJSON>)category.getParentCategories().stream().filter(candidate -> {
              try {
                MainCategory mainCategory = getMainCategoryFromCategory(candidate);
                return (mainCategory != null);
              } catch (IllegalStateException illex) {
                return false;
              } 
            }).collect(Collectors.toSet());
        if (mainCatList.size() == 0 || mainCatList.size() > 1)
          return; 
        for (CategoryJSON catInst : mainCatList) {
          MainCategory mainCategory = getMainCategoryFromCategory(catInst);
          if (mainCategory != null)
            subCategory.setMainCategory(mainCategory); 
        } 
        Set<Category> parentcategories = (Set<Category>)category.getParentCategories().stream().map(candidate -> {
              try {
                return getCategoryEntity(candidate);
              } catch (NoResultException noResEx) {
                throw new IllegalArgumentException("No Result Found for Category: " + candidate.getCategoryName());
              } 
            }).filter(candidate -> (candidate != null)).collect(Collectors.toSet());
        subCategory.setParentCategories(parentcategories);
      } 
      this.entityManager.persist(subCategory);
    } 
  }
      
  public void editSubCategory(CategoryJSON category) {
    if (!(category instanceof SubCategoryJSON))
      return; 
    SubCategory subCategoryEntity = getSubCategoryEntity(category);
    if (subCategoryEntity == null)
      return; 
    SubCategoryJSON subCategory = (SubCategoryJSON)category;
    if (category.getCategoryName() != null && !category.getCategoryName().equalsIgnoreCase(subCategoryEntity.getCat_Name()))
      subCategoryEntity.setCat_Name(category.getCategoryName()); 
    if (subCategory.getParentCategories() != null && subCategory.getParentCategories().size() > 0) {
      Set<Category> parentcategories = (Set<Category>)subCategory.getParentCategories().stream().map(candidate -> {
            try {
              return getCategoryEntity(candidate);
            } catch (NoResultException noResEx) {
              throw new IllegalArgumentException("No Result Found for Category: " + candidate.getCategoryName());
            } 
          }).filter(candidate -> (candidate != null)).collect(Collectors.toSet());
      subCategoryEntity.setParentCategories(parentcategories);
    } 
    this.entityManager.merge(subCategory);
  }
      
  public SubCategory getSubCategoryEntity(CategoryJSON category) {
    SubCategory foundCategory = null;
    if ((category != null) && category.getCategoryId() != 0 && category.getCategoryId() != 1) {
      foundCategory = (SubCategory)this.entityManager.find(SubCategory.class, Integer.valueOf(category.getCategoryId()));
      return foundCategory;
    } 
    Query query = this.entityManager.createNamedQuery("SubCategoryEntity.getCategoryByTitle");
    query.setParameter("title", category.getCategoryName());
    foundCategory = (SubCategory)query.getSingleResult();
    return foundCategory;
  }
      
  public void createMainCategory(CategoryJSON category) {
    if (category == null || (category.getCategoryName() == null && category.getCategoryId() == 0))
      return; 
    MainCategory newCategory = null;
    try {
      Category categoryEntity = getCategoryEntity(category);
      if (categoryEntity != null) {
        newCategory = (MainCategory)this.entityManager.find(MainCategory.class, Integer.valueOf(categoryEntity.getCat_Id()));
        if (newCategory != null)
          return; 
        editCategory(category);
        newCategory = new MainCategory();
        newCategory.setCat_Name(category.getCategoryName());
        this.entityManager.persist(newCategory);
      } 
    } catch (NoResultException ex) {
      createCategory(category);
      newCategory = new MainCategory();
      newCategory.setCat_Name(category.getCategoryName());
      this.entityManager.persist(newCategory);
    } 
  }
      
  public void editMainCategory(CategoryJSON category) {
    if (category instanceof SubCategoryJSON)
      return; 
    editCategory(category);
    MainCategory mainCategoryEntity = getMainCategoryEntity(category);
    if (mainCategoryEntity == null)
      return; 
    if (category.getCategoryId() != 0)
      mainCategoryEntity.setCat_Id(category.getCategoryId()); 
  }
      
  public MainCategory getMainCategoryFromCategory(CategoryJSON category) throws IllegalStateException {
    MainCategory mainCategory = null;
    try {
      mainCategory = getMainCategoryEntity(category);
      return mainCategory;
    } catch (NoResultException ex) {
      if (!(category instanceof SubCategoryJSON))
        return null; 
      for (CategoryJSON candidate : ((SubCategoryJSON)category).getParentCategories()) {
        MainCategory newMainCategory = getMainCategoryFromCategory(candidate);
        if (newMainCategory != null && mainCategory != null)
          throw new IllegalStateException("Category has more than one parent category"); 
      } 
      return null;
    } 
  }
      
  public void removeCategory(CategoryJSON category) {
    if (category == null)
      return; 
    if (category.isCancelled()) {
      Category foundCategory = getCategoryEntity(category);
      if (foundCategory.getTopics() != null)
        return; 
      this.entityManager.remove(foundCategory);
    } 
  }
      
    
      
  public CategoryJSON getMainCategoryInfo(CategoryJSON mainCat) {
    MainCategory mainCategoryEntity = getMainCategoryEntity(mainCat);
    if (mainCategoryEntity != null) {
      CategoryJSON mainCategory = new CategoryJSON(mainCategoryEntity.getCat_Id(), mainCategoryEntity.getCat_Name(),
                                        null, null, null);
      return mainCategory;
    } 
    return null;
  }
      
  public Set<CategoryJSON> getSubCategoryInfo(CategoryJSON category) {
    SubCategory subCatEntity = getSubCategoryEntity(category);
    return (Set<CategoryJSON>)subCatEntity.getParentCategories().stream().map(candidate -> 
                new CategoryJSON(candidate.getCat_Id(), candidate.getCat_Name(), null, null, 
                      null))        
          .collect(Collectors.toSet());
  }
    
  public Set<TopicJSON> getCategoryTopics(CategoryJSON category){
    Category categoryEntity= getCategoryEntity(category);
    Set<TopicJSON> categoryTopics = new HashSet<>();
    categoryTopics = categoryEntity.getTopics().stream().map(topicEntity ->{
      TopicJSON topicJson = new TopicJSON(topicEntity.getTopic_Id(), topicEntity.getTopic().getTitle(), null, null, null, null);
      topicJson.setTopicId(topicEntity.getTopic_Id());
      topicJson.setTitle(topicEntity.getTopic().getTitle());
      return topicJson;
    }).collect(Collectors.toSet());
    return categoryTopics;
  }
      
  public CategoryJSON getCategoryInfo(CategoryJSON category){
    Category categoryEntity = getCategoryEntity(category);
    if(categoryEntity == null)
      return null;
    Set<TopicJSON> categoryTopics = getCategoryTopics(category);
    Set<UserJSON> followers = getCategoryFollowersInfo(category);
    CategoryJSON categoryJson = new CategoryJSON(categoryEntity.getCat_Id(), categoryEntity.getCat_Name(), categoryTopics, followers, null);
    return categoryJson;
  }
}
