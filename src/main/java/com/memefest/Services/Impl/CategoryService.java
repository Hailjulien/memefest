package com.memefest.Services.Impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.memefest.DataAccess.Category;
import com.memefest.DataAccess.CategoryFollower;
import com.memefest.DataAccess.SubCategory;
import com.memefest.DataAccess.SubCategoryId;
import com.memefest.DataAccess.TopicCategory;
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
  
  //throw a custom exception to show object was not created
  public void createCategory(CategoryJSON category) {
    try{
      Category catEntity = getCategoryEntity(category); 
    }
    catch(NoResultException ex){
      Category newCategory = new Category();
      newCategory.setCat_Name(category.getCategoryName());
      this.entityManager.persist(newCategory);
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

  //throw a custom exception to show object was not created
  public void editCategory(CategoryJSON category){
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
      createCategory(category);
    }
    if(category.getTopics() != null && !category.getTopics().isEmpty())
        for (TopicJSON topic : category.getTopics())
          topicOps.createTopic(topic);
    removeCategoryFollowers(category); 
    createCategoryFollowers(category);
    
  }
    
  //throw a custom exception to show object was not created
  public void createCategoryFollowers(CategoryJSON category) {
    if(category.getFollowedBy() == null || category.getFollowedBy().isEmpty())
      return;
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
    try{
      Category foundCategory = getCategoryEntity(category);
      Stream<CategoryFollower> query = this.entityManager.createNamedQuery("CategoryFollower.findByCategoryId", CategoryFollower.class)
                    .setParameter("categoryId", Integer.valueOf(foundCategory.getCat_Id())).getResultStream();
      return (Set<CategoryFollower>)query.map(object -> (CategoryFollower)object).collect(Collectors.toSet());
    }
    catch(NoResultException ex){
      throw new NoResultException();
    }
  }
      
  public void removeCategoryFollowers(CategoryJSON category) {
    if(category.getCancelFollowedBy() == null || category.getCancelFollowedBy().isEmpty())
      return;
    try{
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
    catch(NoResultException ex){
      
    }
  }

      
  public Set<UserJSON> getCategoryFollowersInfo(CategoryJSON category) throws NoResultException{
    try{
      Set<UserJSON> followers = getCategoryFollowers(category).stream().map(categFollowerEntity ->{
        UserJSON user = new UserJSON(categFollowerEntity.getUserId(), categFollowerEntity.getUser().getUsername());
        return user;
      }).collect(Collectors.toSet());
      return followers;
    }
    catch(NoResultException ex){
      throw new NoResultException();
    }
  }
      
  public Category getCategoryEntity(CategoryJSON category) throws NoResultException {
    Category foundCategory = null;
    if((category != null) && category.getCategoryId() != 0 ) {
      foundCategory = (Category)this.entityManager.find(Category.class, Integer.valueOf(category.getCategoryId()));
      if (foundCategory == null){
        throw new NoResultException();
      }          
    }
    else if(category.getCategoryName() != null){   
        foundCategory = this.entityManager.createNamedQuery("Category.getCategoryByTitle", Category.class)
                                .setParameter(1,category.getCategoryName()).getSingleResult();
    }
    else throw new NoResultException();
    return foundCategory; 
  }
  
  /* 
  public MainCategory getMainCategoryEntity(CategoryJSON category) throws NoResultException {
    MainCategory foundCategory = null;
    if ((category != null) && category.getCategoryId() != 0 && category.getCategoryId() != 1) {
      foundCategory = (MainCategory)this.entityManager.find(MainCategory.class, Integer.valueOf(category.getCategoryId()));
      if (foundCategory != null)
        return foundCategory;
    } 
    Query query = this.entityManager.createNativeQuery("MainCategory.getCategoryByTitle");
    query.setParameter("title",category.getCategoryName());
    foundCategory = (MainCategory)query.getSingleResult();
    return foundCategory;
  }
  */
  
  public void editSubCategory(SubCategoryJSON subCategory){
    /*MainCategory mainCategory = null;
    try{
      mainCategory = getMainCategoryFromCategory(subCategory);
    }
    catch(NoResultException ex){
      createMainCategory(subCategory.getMainCategoryJSON());
      editSubCategory(subCategory);
    }
    */
    editCategory(subCategory);
    Category categoryEntity = getCategoryEntity(subCategory);

    for(CategoryJSON candidate : subCategory.getParentCategories()){
      Category candidateEntity = null;
      try{
        editCategory(candidate);
        candidateEntity = getCategoryEntity(candidate);
      }
      catch(NoResultException ex){
        continue;
      }
      try{
       getSubCategoryEntity(categoryEntity, candidateEntity);
      }
      catch(NoResultException ex){  
        createParentCategories(categoryEntity, candidateEntity);
      }
    }
    for(CategoryJSON candidate : subCategory.getCanceledParentCategories()){
      try{
          Category candidateEntity = getCategoryEntity(candidate);
          SubCategory subCatEntity = getSubCategoryEntity(categoryEntity, candidateEntity);
          entityManager.remove(subCatEntity);
      } 
      catch(NoResultException ex){
        continue;
      }
    }
  }

  private void createParentCategories(Category category, Category parentCategory){  
      SubCategory subCat= new SubCategory();
      subCat.setCategory(category);
      subCat.setParent(parentCategory);
      entityManager.persist(subCat);
  }

  private SubCategory getSubCategoryEntity(Category category, Category parentCategory) throws NoResultException{
    SubCategoryId subCategoryId = new SubCategoryId();
    subCategoryId.setCat_Id(category.getCat_Id());
    subCategoryId.setParent_Id(parentCategory.getCat_Id());
    return entityManager.find(SubCategory.class, subCategoryId);
  }
/*
  //throw a custom exception to show object was not created    
  public void createSubCategory(SubCategoryJSON category) throws NoResultException{



    

 
    if (category == null || (category.getCategoryName() == null && category.getCategoryId() == 0))
      return; 
    
    try {
     Category categoryEntity = getCategoryEntity((CategoryJSON)category);
      /*
      if (category.getParentCategories() == null || category.getParentCategories().size() == 0) {
        Set<CategoryJSON> mainCatList = (Set<CategoryJSON>)category.getParentCategories().stream().filter(candidate -> {
              try {subCategory = new SubCategory();
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
        subCategory.setSubcategories(null); setParentCategories(parentcategories.stream().filter(candidate ->{
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
              } 
            }).collect(Collectors.toSet());
        if (mainCatList.size() == 0 || mainCatList.size() > 1)
          return; 
        for (CategoryJSON catInst : mainCatList) {
          try{
            MainCategory mainCategory = getMainCategoryFromCategory(catInst);
            SubCategory subCategory = getSubCategoryEntity(catInst);

            mainCategory.setSubcategories();
            subCat.setMainCategory(mainCategory);
          catch(NoResultException ex){

          } 
        } 
        Set<Category> parentcategories = (Set<Category>)category.getParentCategories().stream().map(candidate -> {
              try {
                return getCategoryEntity(candidate);
              } catch (NoResultException noResEx) {
                throw new IllegalArgumentException("No Result Found for Category: " + candidate.getCategoryName());
              } 
            }).filter(candidate -> (candidate != null)).collect(Collectors.toSet());
        subCategory.setSubcategories(null); setParentCategories(parentcategories.stream().filter(candidate ->{
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
    } catch (NoResultException ex) {
      createCategory(category);
      
    }
    
  }
  
  //throw a custom exception to show object was not created
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
    */
      
  public SubCategory getSubCategoryEntity(CategoryJSON category, CategoryJSON parent) throws NoResultException{
    SubCategory foundCategory = null;
    if (((category != null) && category.getCategoryId() != 0) || 
            ((parent != null)  && parent.getCategoryId() != 0)) {
      SubCategoryId subCatId = new SubCategoryId();
      subCatId.setCat_Id(category.getCategoryId());
      subCatId.setParent_Id(parent.getCategoryId());
      foundCategory = (SubCategory)this.entityManager.find(SubCategory.class, subCatId);
      if(foundCategory != null)
        return foundCategory;
      else throw new NoResultException();
    }
    else throw new NoResultException();
  }
  /* 
  //throw a custom exception to show object was not created
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
      
  //throw a custom exception to show object was not created
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
  */
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
/*  
  public CategoryJSON getMainCategoryInfo(CategoryJSON mainCat) throws NoResultException {
    MainCategory mainCategoryEntity = getMainCategoryEntity(mainCat);
    CategoryJSON mainCategory = new CategoryJSON(mainCategoryEntity.getCat_Id(), mainCategoryEntity.getCat_Name(),
                                        null, null, null);
    return mainCategory;
  }
*/
  public SubCategoryJSON getSubCategoryInfo(SubCategoryJSON category) throws NoResultException{
    CategoryJSON categoryInfo = getCategoryInfo(category);
    Set<CategoryJSON> parentCategories = new HashSet<CategoryJSON>();
    try{
      Stream<SubCategory> subCats =  entityManager.createNamedQuery("SubCategory.getParentCategories",SubCategory.class)
          .setParameter("parentId", categoryInfo.getCategoryId()).getResultStream();
      parentCategories = subCats.map(candidate ->{
      return getCategoryInfo(new CategoryJSON(candidate.getParent().getCat_Id(), null, null, null, null));
              }).collect(Collectors.toSet());
    }
    catch(NoResultException ex){

    }
    SubCategoryJSON subCategoryJSON = new SubCategoryJSON(0, null, null, null, parentCategories);
    subCategoryJSON.setFollowedBy(categoryInfo.getFollowedBy());
    subCategoryJSON.setCategoryName(categoryInfo.getCategoryName());
    subCategoryJSON.setCategoryId(categoryInfo.getCategoryId()); 
    return subCategoryJSON;
    /*
    SubCategory subCatEntity = getSubCategoryEntity(category);
    return (Set<CategoryJSON>)subCatEntity.getParentCategories().stream().map(candidate -> 
                new CategoryJSON(candidate.getCat_Id(), candidate.getCat_Name(), null, null, 
                      null))        
          .collect(Collectors.toSet());
    */
  }
    
  public Set<TopicJSON> getCategoryTopics(CategoryJSON category)throws NoResultException{
    Category catEntity = getCategoryEntity(category);
    Set<TopicJSON> topics = new HashSet<TopicJSON>();
    Stream<TopicCategory> topicCats = entityManager.createNamedQuery("TopicCategory.getByCategoryId", TopicCategory.class)
              .setParameter("categoryId", catEntity.getCat_Id()).getResultStream();
    topics = topicCats.map(candidate ->{
      return new TopicJSON(candidate.getTopic_Id() , null, null, null, null, null);
    }).collect(Collectors.toSet());
    return topics;
    /*Category categoryEntity= getCategoryEntity(category);
    return categoryEntity.getTopics().stream().map(topicEntity ->{
      TopicJSON topicJson = new TopicJSON(topicEntity.getTopic_Id(), topicEntity.getTitle(), null, null, null, null);
      topicJson.setTopicId(topicEntity.getTopic_Id());
      topicJson.setTitle(topicEntity.getTitle());
      return topicJson;
    }).collect(Collectors.toSet());
    */
  }
      
  public CategoryJSON getCategoryInfo(CategoryJSON category) throws NoResultException{
      Category categoryEntity = getCategoryEntity(category);
      Set<TopicJSON> categoryTopics = null;
      Set<UserJSON> followers = null;
      try{
        followers = getCategoryFollowersInfo(category);
      }
      catch(NoResultException ex){  
      }
      try{
        categoryTopics = getCategoryTopics(category);
      }
      catch(NoResultException ex){
      }
      CategoryJSON categoryJson = new CategoryJSON(categoryEntity.getCat_Id(), categoryEntity.getCat_Name(), categoryTopics, followers, null);
      return categoryJson;
  }

}
