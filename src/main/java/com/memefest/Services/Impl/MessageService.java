package com.memefest.Services.Impl;

import com.memefest.DataAccess.Category;
import com.memefest.DataAccess.CategoryFollower;
import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import com.memefest.DataAccess.JSON.SubCategoryJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.DataAccess.MainCategory;
import com.memefest.DataAccess.Post;
import com.memefest.DataAccess.PostReply;
import com.memefest.DataAccess.SubCategory;
import com.memefest.DataAccess.Topic;
import com.memefest.DataAccess.TopicCategory;
import com.memefest.DataAccess.TopicCategoryId;
import com.memefest.DataAccess.TopicFollower;
import com.memefest.DataAccess.TopicFollowerId;
import com.memefest.DataAccess.User;
import com.memefest.Services.TopicOperations;
import com.memefest.Services.UserOperations;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.Query;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DataSourceDefinition(
  name = "java:app/jndi/memefest/dataSourcessde",
  url = "jdbc:sqlserver://;servername=CHHUMBUCKET;DatabaseName=MemeFest;trustServerCertificate=true",
  className = "com.microsoft.sqlserver.jdbc.SQLServerDataSource",
  user = "Neutron",
  password = "scoobyDoo24"
)
@Stateful
public class MessageService implements TopicOperations{
  @PersistenceContext(unitName = "memeFest", type = PersistenceContextType.TRANSACTION)
  private EntityManager entityManager;
  
  @EJB
  private UserOperations userOperations;
  
  private void createPost(Post post) {
    this.entityManager.persist(post);
  }
  
  private void createCategory(Category category) {
    this.entityManager.persist(category);
  }
  
  private void createTopic(Topic user) {
    this.entityManager.persist(user);
  }
  
  public void createPost(PostJSON post) {
    Topic topic = null;
    topic = getTopicEntity(post.getTopic());
    if (topic == null) {
      createTopic(topic);
      createPost(post);
      return;
    } 
    User user = null;
    user = this.userOperations.getUserEntity(post.getUser());
    if (user == null);
    Post newPost = new Post();
    newPost.setComment(post.getComment());
    newPost.setCreated(Date.from(post.getCreated().atZone(ZoneId.systemDefault()).toInstant()));
    newPost.setUser(user);
    newPost.setTopic(topic);
    newPost.setUpvotes(post.getUpvotes());
    newPost.setDownvotes(post.getDownvotes());
    createPost(newPost);
    if (post instanceof PostWithReplyJSON)
      createPostReplys((PostWithReplyJSON)post); 
  }
  
  public void createPostReplys(PostWithReplyJSON post) {
    for (PostJSON postInst : post.getPosts()) {
      Post parent = getPostEntity((PostJSON)post);
      if (parent == null)
        return; 
      Post child = getPostEntity(postInst);
      if (child != null)
        continue; 
      PostReply postReply = new PostReply();
      postReply.setComment(postInst.getComment());
      postReply.setCreated(Date.from(postInst.getCreated().atZone(ZoneId.systemDefault()).toInstant()));
      postReply.setPost(parent);
      postReply.setUser(this.userOperations.getUserEntity(postInst.getUser()));
      postReply.setDownvotes(postInst.getDownvotes());
      postReply.setUpvotes(postInst.getUpvotes());
      postReply.setTopic(parent.getTopic());
      this.entityManager.persist(postReply);
      if (postInst instanceof PostWithReplyJSON)
        createPostReplys((PostWithReplyJSON)postInst); 
    } 
  }
  
  public void removePostReplys(PostWithReplyJSON postWithReply) {
    for (PostJSON post : postWithReply.getPosts()) {
      if (post.isCancelled()) {
        Post postEntity = getPostEntity(post);
        if (postEntity != null)
          this.entityManager.remove(postEntity); 
      } 
    } 
  }
  
  private void editPost(PostJSON post) {
    if (post instanceof PostWithReplyJSON) {
      PostWithReplyJSON postWithReply = (PostWithReplyJSON)post;
      Post postEntity = getPostEntity((PostJSON)postWithReply);
      if (postEntity != null && post.getPostId() != 0) {
        for (PostJSON postInst : postWithReply.getPosts()) {
          Post replyEntity = getPostEntity(postInst);
          if (replyEntity != null) {
            PostReply newReply = null;
            User user = this.userOperations.getUserEntity(postInst.getUser());
            Topic topic = getTopicEntity(postInst.getTopic());
            newReply = (PostReply)this.entityManager.find(PostReply.class, Integer.valueOf(replyEntity.getPost_Id()));
            if (newReply == null) {
              createPostReplys((PostWithReplyJSON)post);
              editPost(post);
            } 
            if (topic == null)
              createTopic(topic); 
            if (user != null) {
              newReply.setUser(user);
              if (postInst.getComment() != null)
                newReply.setComment(postInst.getComment()); 
              if (postInst.getTopic() != null)
                newReply.setPost(postEntity); 
              if (postInst.getUser() != null)
                newReply.setUser(user); 
              if (postInst.getTopic() != null)
                newReply.setTopic(topic); 
              if (postInst.getCreated() != null)
                newReply.setCreated(Date.from(postInst.getCreated().atZone(ZoneId.systemDefault()).toInstant())); 
              if (newReply.getDownvotes() != 0)
                newReply.setDownvotes(postInst.getDownvotes()); 
              if (postInst.getUpvotes() != 0)
                newReply.setUpvotes(postInst.getUpvotes()); 
              newReply.setPost(postEntity);
              this.entityManager.merge(newReply);
              editPost(postInst);
            } 
          } 
        } 
      } else {
        createPost(post);
        editPost(post);
        return;
      } 
    } else {
      Post postEntity = null;
      postEntity = getPostEntity(post);
      if (postEntity == null) {
        createPost(post);
        editPost(post);
        return;
      } 
      User user = this.userOperations.getUserEntity(post.getUser());
      Topic topic = getTopicEntity(post.getTopic());
      if (topic == null)
        createTopic(topic); 
      if (user != null)
        postEntity.setUser(user); 
      if (post.getComment() != null)
        postEntity.setComment(post.getComment()); 
      if (topic != null)
        postEntity.setTopic(topic); 
      if (post.getCreated() != null)
        postEntity.setCreated(Date.from(post.getCreated().atZone(ZoneId.systemDefault()).toInstant())); 
      if (postEntity.getDownvotes() != 0)
        postEntity.setDownvotes(post.getDownvotes()); 
      if (postEntity.getUpvotes() != 0)
        postEntity.setUpvotes(post.getUpvotes()); 
      this.entityManager.merge(postEntity);
    } 
    removePost(post);
  }
  
  private Post getPostEntity(PostJSON post) {
    if (post.getPostId() != 0)
      return (Post)this.entityManager.find(Post.class, Integer.valueOf(post.getPostId())); 
    if (post.getComment() != null) {
      Query query = this.entityManager.createNamedQuery("Post.getPostByComment");
      query.setParameter("comment", post.getComment());
      return (Post)query.getSingleResult();
    } 
    return null;
  }
  
  public void removePost(PostJSON post) {
    if (post.isCancelled()) {
      Post postEntity = getPostEntity(post);
      if (postEntity != null) {
        if (post instanceof PostWithReplyJSON)
          removePostReplys((PostWithReplyJSON)post); 
        this.entityManager.remove(postEntity);
      } 
    } 
  }
  
  public void createTopic(TopicJSON topic) {
    Topic foundTopic = getTopicEntity(topic);
    if (foundTopic == null)
      return; 
    foundTopic.setTitle(topic.getTitle());
    foundTopic.setCreated(Date.from(topic.getCreated().atZone(ZoneId.systemDefault()).toInstant()));
    this.entityManager.persist(foundTopic);
    createTopicCategories(topic);
    removeTopicCategories(topic);
  }
  
  public Set<TopicCategory> getTopicCategories(TopicJSON topic) {
    Topic topicEntity = getTopicEntity(topic);
    if (topicEntity == null)
      return null; 
    Set<TopicCategory> topicCategories = new HashSet<>();
    for (CategoryJSON category : topic.getCategories()) {
      Category catEntity = getCategoryEntity(category);
      TopicCategory topicCategory = getTopicCategory(topicEntity, catEntity);
      if (topicCategory != null)
        topicCategories.add(topicCategory); 
    } 
    return topicCategories;
  }
  
  public void createTopicCategories(TopicJSON topic) {
    Topic foundTopic = getTopicEntity(topic);
    if (foundTopic != null)
      createTopic(topic); 
    Set<CategoryJSON> categories = topic.getCategories();
    for (CategoryJSON category : categories) {
      Category catEntity = getCategoryEntity(category);
      TopicCategory topicCategory = getTopicCategory(foundTopic, catEntity);
      if (topicCategory == null) {
        topicCategory = new TopicCategory();
        topicCategory.setTopic(foundTopic);
        topicCategory.setCategory(catEntity);
        this.entityManager.persist(topicCategory);
      } 
    } 
  }
  
  public void removeTopicCategories(TopicJSON topic) {
    Topic foundTopic = getTopicEntity(topic);
    if (foundTopic == null)
      return; 
    Set<CategoryJSON> categories = topic.getCancelCategories();
    for (CategoryJSON cat : categories) {
      Category catEntity = getCategoryEntity(cat);
      TopicCategory topicCategory = getTopicCategory(foundTopic, catEntity);
      if (topicCategory != null)
        this.entityManager.remove(topicCategory); 
    } 
  }
  
  private TopicCategory getTopicCategory(Topic topic, Category category) {
    if (category == null || category == null)
      return null; 
    TopicCategoryId topicCatId = new TopicCategoryId();
    topicCatId.setTopic_Id(topic.getTopic_Id());
    topicCatId.setCat_Id(category.getCat_Id());
    TopicCategory foundTopicCategory = (TopicCategory)this.entityManager.find(TopicCategory.class, new TopicFollowerId());
    return foundTopicCategory;
  }
  
  public Topic getTopicEntity(TopicJSON topic) {
    Topic foundTopic = null;
    if ((((topic != null) ? 1 : 0) & ((topic.getTopicId() != 0) ? 1 : 0)) != 0) {
      foundTopic = (Topic)this.entityManager.find(Topic.class, Integer.valueOf(topic.getTopicId()));
      return foundTopic;
    } 
    Query query = this.entityManager.createNamedQuery("Topic.getTopicByTitle");
    query.setParameter("title", topic.getTitle());
    foundTopic = (Topic)query.getSingleResult();
    return foundTopic;
  }
  
  public void createTopicFollower(TopicJSON topic) {
    Topic foundTopic = getTopicEntity(topic);
    if (foundTopic == null)
      return; 
    for (UserJSON user : topic.getFollowedBy()) {
      User newFollower = this.userOperations.getUserEntity(user);
      if (newFollower == null)
        return; 
      TopicFollower follower = getTopicFollower(user, topic);
      if (follower == null) {
        TopicFollower newTopicFollower = new TopicFollower();
        newTopicFollower.setTopic(foundTopic);
        newTopicFollower.setFollower(newFollower);
        this.entityManager.persist(newFollower);
      } 
    } 
  }
  
  public TopicFollower getTopicFollower(UserJSON user, TopicJSON topic) {
    TopicFollower foundFollower = null;
    if (user != null && topic != null && user.getUserId() != 0 && topic.getTopicId() != 0) {
      TopicFollowerId followerId = new TopicFollowerId();
      followerId.setUserId(user.getUserId());
      followerId.setTopic_Id(topic.getTopicId());
      foundFollower = (TopicFollower)this.entityManager.find(TopicFollower.class, followerId);
      return foundFollower;
    } 
    if (user != null && topic != null) {
      Topic topicEntity = getTopicEntity(topic);
      User userEntity = this.userOperations.getUserEntity(user);
      if (topicEntity == null || userEntity == null);
      user.setUserId(user.getUserId());
      topic.setTopicId(topic.getTopicId());
      return getTopicFollower(user, topic);
    } 
    return null;
  }
  
  public void removeTopicFollowers(TopicJSON topic) {
    Set<UserJSON> followers = topic.getFollowedBy();
    Set<UserJSON> canceledFollowers = (Set<UserJSON>)followers.stream().filter(candidate -> candidate.isCancelled()).collect(Collectors.toSet());
    if (canceledFollowers != null && canceledFollowers.size() > 0)
      for (UserJSON user : canceledFollowers) {
        Topic topicEntity = getTopicEntity(topic);
        if (topicEntity != null) {
          TopicFollower follower = getTopicFollower(user, topic);
          if (follower != null)
            this.entityManager.remove(follower); 
        } 
      }  
  }
  
  public void editTopicFollowers(TopicJSON topic) {
    Topic topicEntity = getTopicEntity(topic);
    if (topicEntity != null)
      return; 
    Set<UserJSON> followers = topic.getFollowedBy();
    Set<UserJSON> updatedFollowers = (Set<UserJSON>)followers.stream().filter(candidate -> !candidate.isCancelled()).collect(Collectors.toSet());
    Set<UserJSON> deletedFollowers = (Set<UserJSON>)followers.stream().filter(candidate -> candidate.isCancelled()).collect(Collectors.toSet());
    if (updatedFollowers != null && updatedFollowers.size() > 0)
      for (UserJSON user : updatedFollowers) {
        TopicFollower follower = getTopicFollower(user, topic);
        if (follower == null)
          createTopicFollower(topic); 
      }  
    if (deletedFollowers != null && deletedFollowers.size() > 0)
      for (UserJSON user : deletedFollowers) {
        TopicFollower follower = getTopicFollower(user, topic);
        if (follower != null)
          this.entityManager.remove(follower); 
      }  
  }
  
  public void editTopic(TopicJSON topic) {
    Topic foundTopic = getTopicEntity(topic);
    if (foundTopic == null)
      return; 
    if (topic.getTitle() != null)
      foundTopic.setTitle(topic.getTitle()); 
    if (topic.getCreated() != null)
      foundTopic.setCreated(Date.from(topic.getCreated().atZone(ZoneId.systemDefault()).toInstant())); 
    if (topic.getPosts() != null && topic.getPosts().size() > 0)
      for (PostJSON post : topic.getPosts())
        editPost(post);  
    if (topic.getCategories() != null && topic.getCategories().size() > 0)
      for (CategoryJSON category : topic.getCategories())
        editCategory(category);  
    if (topic.getFollowedBy() != null && topic.getFollowedBy().size() > 0)
      editTopicFollowers(topic); 
    this.entityManager.merge(foundTopic);
  }
  
  public void removeTopic(TopicJSON topic) {
    if (topic.isCancelled()) {
      Topic foundTopic = getTopicEntity(topic);
      if (foundTopic != null) {
        for (PostJSON post : topic.getPosts())
          removePost(post); 
        removeTopicFollowers(topic);
        this.entityManager.remove(foundTopic);
      } 
    } 
  }
  
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
      createTopic(topic); 
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
      editTopic(topic); 
    removeCategoryFollowers(category);
    createCategoryFollowers(category);
  }
  
  public void createCategoryFollowers(CategoryJSON category) {
    Category foundCategory = getCategoryEntity(category);
    if (foundCategory == null);
    Set<CategoryFollower> catFollowers = getCategoryFollowers(category);
    Set<User> catUsers = (Set<User>)catFollowers.stream().map(catFollower -> catFollower.getUser()).collect(Collectors.toSet());
    for (UserJSON user : category.getFollowedBy()) {
      User follower = this.userOperations.getUserEntity(user);
      if (follower == null);
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
  
  public Category getCategoryEntity(CategoryJSON category) throws NoResultException {
    Category foundCategory = null;
    if ((((category != null) ? 1 : 0) & ((category.getCategoryId() != 0) ? 1 : 0)) != 0) {
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
    if ((((category != null) ? 1 : 0) & ((category.getCategoryId() != 0) ? 1 : 0)) != 0) {
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
    if ((((category != null) ? 1 : 0) & ((category.getCategoryId() != 0) ? 1 : 0)) != 0) {
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
  
  private MainCategory getMainCategoryFromCategory(CategoryJSON category) throws IllegalStateException {
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
  
  public Set<PostReply> getPostReplyEntities(PostWithReplyJSON postWithReply) {
    Set<PostReply> result = new HashSet<>();
    Query query = this.entityManager.createNamedQuery("PostReplyEntity.getRepliesOfPostId");
    query.setParameter("postId", Integer.valueOf(postWithReply.getPostId()));
    List<PostReply> postReplies = query.getResultList();
    result = (Set<PostReply>)postReplies.stream().collect(Collectors.toSet());
    return result;
  }
  
  public PostWithReplyJSON getPostWithReplyInfo(PostWithReplyJSON postWithReply) {
    Set<PostReply> postReplyEntities = getPostReplyEntities(postWithReply);
    if (postReplyEntities == null)
      return null; 
    Set<PostJSON> postReplys = (Set<PostJSON>)postReplyEntities.stream().map(candidate -> new PostJSON(candidate.getPost_Id(), null, null, 0, 0, null, null)).collect(Collectors.toSet());
    postWithReply.setPosts(postReplys);
    return postWithReply;
  }
  
  public CategoryJSON getMainCategoryInfo(CategoryJSON mainCat) {
    MainCategory mainCategoryEntity = getMainCategoryEntity(mainCat);
    if (mainCategoryEntity != null) {
      CategoryJSON mainCategory = new CategoryJSON(mainCategoryEntity.getCat_Id(), mainCategoryEntity.getCat_Name(), null, null, null);
      return mainCategory;
    } 
    return null;
  }
  
  public Set<CategoryJSON> getSubCategoryInfo(CategoryJSON category) {
    SubCategory subCatEntity = getSubCategoryEntity(category);
    return (Set<CategoryJSON>)subCatEntity.getParentCategories().stream().map(candidate -> new CategoryJSON(candidate.getCat_Id(), candidate.getCat_Name(), null, null, null))
      
      .collect(Collectors.toSet());
  }
  
  public TopicJSON getTopicInfo(TopicJSON topic) {
    Topic topicEntity = getTopicEntity(topic);
    if (topicEntity == null)
      return null; 
    Set<TopicFollower> topicFollowers = topicEntity.getFollowedBy();
    Set<CategoryJSON> categories = (Set<CategoryJSON>)topicEntity.getCategories().stream().map(catInst -> new CategoryJSON(catInst.getCategory().getCat_Id(), catInst.getCategory().getCat_Name(), null, null, null)).collect(Collectors.toSet());
    Set<UserJSON> users = (Set<UserJSON>)topicFollowers.stream().map(topicFollower -> new UserJSON(topicFollower.getUser().getUsername())).collect(Collectors.toSet());
    Set<PostJSON> posts = (Set<PostJSON>)topicEntity.getPosts().stream().map(topicPost -> new PostJSON(topicPost.getPost_Id(), topicPost.getComment(), LocalDateTime.ofInstant(topicPost.getCreated().toInstant(), ZoneId.systemDefault()), topicPost.getUpvotes(), topicPost.getDownvotes(), new UserJSON(topicPost.getUser().getUsername()), new TopicJSON(topicPost.getTopic().getTopic_Id(), topicPost.getTopic().getTitle(), LocalDateTime.ofInstant(topicPost.getTopic().getCreated().toInstant(), ZoneId.systemDefault()), null, null, null))).collect(Collectors.toSet());
    if (topicEntity != null) {
      TopicJSON topicJSON = new TopicJSON(topic.getTopicId(), topic.getTitle(), LocalDateTime.ofInstant(topicEntity.getCreated().toInstant(), ZoneId.systemDefault()), categories, posts, users);
      return topicJSON;
    } 
    return null;
  }
  
  public CategoryJSON getCategoryInfo(CategoryJSON category) {
    Category catEntity = getCategoryEntity(category);
    Set<TopicJSON> topics = (Set<TopicJSON>)catEntity.getTopics().stream().map(candidate -> new TopicJSON(candidate.getTopic().getTopic_Id(), candidate.getTopic().getTitle(), null, null, null, null)).collect(Collectors.toSet());
    if (category != null) {
      CategoryJSON categoryJSON = new CategoryJSON(catEntity.getCat_Id(), catEntity.getCat_Name(), topics, null, null);
      return categoryJSON;
    } 
    return null;
  }
  
  public PostJSON getPostInfo(PostJSON post) {
    Post postEntity = getPostEntity(post);
    UserJSON user = this.userOperations.getUserInfo(post.getUser());
    TopicJSON topic = getTopicInfo(post.getTopic());
    if (post != null) {
      PostJSON postJSON = new PostJSON(postEntity.getPost_Id(), postEntity.getComment(), LocalDateTime.ofInstant(postEntity.getCreated().toInstant(), ZoneId.systemDefault()), post.getUpvotes(), post.getDownvotes(), new UserJSON(user.getUsername()), topic);
      return postJSON;
    } 
    return null;
  }
}
