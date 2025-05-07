package com.memefest.Services.Impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    try{
        if(getCategoryEntity(category) != null)
        return; 
      Category newCategory = new Category();
      newCategory.setCat_Name(category.getCategoryName());
      this.entityManager.persist(newCategory);
      createCategoryFollowers(category);
      for (TopicJSON topic : category.getTopics())
        topicOps.createTopic(topic); 
      }
    catch(NoResultException ex){
        return;
    }
  } 
      
  public Set<CategoryJSON> searchCategory(CategoryJSON category){
    if(category !=  null && category.getCategoryName() != null){
      Stream<Category> query = entityManager.createNamedQuery("Category.searchByName", Category.class)
                            .setParameter("title","%" + category.getCategoryName() + "%").getResultStream();
      return query.map(categoryInst->{
        return new CategoryJSON(categoryInst.getCat_Id(), null, null, null, null);
      }).collect(Collectors.toSet());
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
    try{
      Category foundCategory = getCategoryEntity(category);
      if (category.getCategoryName() != null)
        foundCategory.setCat_Name(category.getCategoryName()); 
      if (category.getCategoryId() != 0)
        foundCategory.setCat_Id(category.getCategoryId());
    } 
    catch(NoResultException ex){
      return;
    }
    for (TopicJSON topic : category.getTopics())
        topicOps.editTopic(topic); 
    removeCategoryFollowers(category);
    createCategoryFollowers(category);
  }
      
  public void createCategoryFollowers(CategoryJSON category) {
    try{
      Category foundCategory = getCategoryEntity(category);
      Set<User> catUsers = new HashSet<User>();
      try{
        Set<CategoryFollower> catFollowers = getCategoryFollowers(category);
        catUsers = (Set<User>)catFollowers.stream().map(catFollower -> catFollower.getUser()).collect(Collectors.toSet()); 
      }
      catch(NoResultException ex){
       return; 
      }
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
    catch(NoResultException ex){
      return;
    }
  }
      
  public Set<CategoryFollower> getCategoryFollowers(CategoryJSON category) throws NoResultException{
    Category foundCategory = getCategoryEntity(category);
    Stream<CategoryFollower> query = this.entityManager.createNamedQuery("CategoryFollower.findByCategoryId", CategoryFollower.class)
                    .setParameter("categoryId", Integer.valueOf(foundCategory.getCat_Id())).getResultStream();
    return (Set<CategoryFollower>)query.map(object -> (CategoryFollower)object).collect(Collectors.toSet());
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

      
  public Set<UserJSON> getCategoryFollowersInfo(CategoryJSON category) throws NoResultException{
    Set<UserJSON> followers = getCategoryFollowers(category).stream().map(categFollowerEntity ->{
      UserJSON user = new UserJSON(categFollowerEntity.getUserId(), categFollowerEntity.getUser().getUsername());
      return user;
    }).collect(Collectors.toSet());
    return followers;
  }
      
  public Category getCategoryEntity(CategoryJSON category) throws NoResultException {
    Category foundCategory = null;
    if((category != null) && category.getCategoryId() != 0 && category.getCategoryId() != 1) {
      foundCategory = (Category)this.entityManager.find(Category.class, Integer.valueOf(category.getCategoryId()));
      if (foundCategory == null){
        return foundCategory;
      }          
    } 
    Query query = this.entityManager.createNamedQuery("Category.getCategoryByTitle");
    query.setParameter("title", "%" + category.getCategoryName() + "%");
    foundCategory = (Category)query.getSingleResult();
    return foundCategory;
  }
      
  public MainCategory getMainCategoryEntity(CategoryJSON category) throws NoResultException {
    MainCategory foundCategory = null;
    if ((category != null) && category.getCategoryId() != 0 && category.getCategoryId() != 1) {
      foundCategory = (MainCategory)this.entityManager.find(MainCategory.class, Integer.valueOf(category.getCategoryId()));
      if (foundCategory != null)
        return foundCategory;
    } 
    Query query = this.entityManager.createNamedQuery("MainCategory.getCategoryByTitle");
    query.setParameter("title", "%" + category.getCategoryName() + "%");
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
        subCategory.setParentCategories(parentcategories.stream().filter(candidate ->{
           return (candidate instanceof SubCategory);
        }).map(candidateEntity ->{
          try{
            return entityManager.find(SubCategory.class, candidateEntity.getCat_Id());
          }
          catch(NoResultException exp){
            ex.printStackTrace();
          }
          return null;
        }).collect(Collectors.toSet()));
      } 
      this.entityManager.persist(subCategory);
    } 
  }
      
  public void editSubCategory(CategoryJSON category) {
    if (!(category instanceof SubCategoryJSON))
      return;
    try{ 
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
    subCategoryEntity.setParentCategories(parentcategories.stream().filter(candidate ->{
                                                return (candidate instanceof SubCategory);
                                             }).map(candidateEntity ->{
                                               try{
                                                 return entityManager.find(SubCategory.class, candidateEntity.getCat_Id());
                                               }
                                               catch(NoResultException exp){
                                                 exp.printStackTrace();
                                               }
                                               return null;
                                             }).collect(Collectors.toSet()));
                                           } 
      this.entityManager.merge(subCategory);
    }   
    catch(NoResultException ex){
      return;
    }
  }
      
  public SubCategory getSubCategoryEntity(CategoryJSON category) throws NoResultException{
    SubCategory foundCategory = null;
    if ((category != null) && category.getCategoryId() != 0 && category.getCategoryId() != 1) {
      foundCategory = (SubCategory)this.entityManager.find(SubCategory.class, Integer.valueOf(category.getCategoryId()));
      if(foundCategory != null)
        return foundCategory;
    } 
    Query query = this.entityManager.createNamedQuery("SubCategoryEntity.getCategoryByTitle");
    query.setParameter("title", "%" + category.getCategoryName() + "%");
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
        if (newCategory == null)
          createCategory(category);
        else
        editCategory(category);
        this.entityManager.persist(newCategory);
      } 
    } catch (NoResultException ex) {
      return;
    } 
  }
      
  public void editMainCategory(CategoryJSON category) {
    if (category instanceof SubCategoryJSON)
      return; 
    
    try{
      MainCategory mainCategoryEntity = getMainCategoryEntity(category);
      if(mainCategoryEntity.getSubcategories() != null && category.isCancelled()){
        entityManager.remove(mainCategoryEntity);
        return;
      }
    }
    catch(NoResultException ex){
      return;
    }
    editCategory(category); 
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
      
    
      
  public CategoryJSON getMainCategoryInfo(CategoryJSON mainCat) throws NoResultException {
    MainCategory mainCategoryEntity = getMainCategoryEntity(mainCat);
    CategoryJSON mainCategory = new CategoryJSON(mainCategoryEntity.getCat_Id(), mainCategoryEntity.getCat_Name(),
                                        null, null, null);
    return mainCategory;
  }
      
  public Set<CategoryJSON> getSubCategoryInfo(CategoryJSON category) throws NoResultException{
    SubCategory subCatEntity = getSubCategoryEntity(category);
    return (Set<CategoryJSON>)subCatEntity.getParentCategories().stream().map(candidate -> 
                new CategoryJSON(candidate.getCat_Id(), candidate.getCat_Name(), null, null, 
                      null))        
          .collect(Collectors.toSet());
  }
    
  public Set<TopicJSON> getCategoryTopics(CategoryJSON category)throws NoResultException{
    Category categoryEntity= getCategoryEntity(category);
    return categoryEntity.getTopics().stream().map(topicEntity ->{
      TopicJSON topicJson = new TopicJSON(topicEntity.getTopic_Id(), topicEntity.getTitle(), null, null, null, null);
      topicJson.setTopicId(topicEntity.getTopic_Id());
      topicJson.setTitle(topicEntity.getTitle());
      return topicJson;
    }).collect(Collectors.toSet());
  }
      
  public CategoryJSON getCategoryInfo(CategoryJSON category) throws NoResultException{
    Category categoryEntity = getCategoryEntity(category);
    Set<TopicJSON> categoryTopics = getCategoryTopics(category);
    Set<UserJSON> followers = getCategoryFollowersInfo(category);
    CategoryJSON categoryJson = new CategoryJSON(categoryEntity.getCat_Id(), categoryEntity.getCat_Name(), categoryTopics, followers, null);
    return categoryJson;
  }
}
