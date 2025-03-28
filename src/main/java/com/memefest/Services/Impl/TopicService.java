package com.memefest.Services.Impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.memefest.DataAccess.Category;
import com.memefest.DataAccess.Post;
import com.memefest.DataAccess.Topic;
import com.memefest.DataAccess.TopicCategory;
import com.memefest.DataAccess.TopicCategoryId;
import com.memefest.DataAccess.TopicFollower;
import com.memefest.DataAccess.TopicFollowerId;
import com.memefest.DataAccess.User;
import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.CategoryOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Services.UserOperations;
import com.memefest.Websockets.JSON.FollowNotificationJSON;
import com.memefest.Websockets.JSON.InteractNotificationJSON;

import jakarta.annotation.Resource;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.EJB;
import jakarta.ejb.ScheduleExpression;
import jakarta.ejb.Stateless;
import jakarta.ejb.Timeout;
import jakarta.ejb.Timer;
import jakarta.ejb.TimerConfig;
import jakarta.ejb.TimerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.Query;


@DataSourceDefinition(
  name = "java:app/jndi/memefest/dataSourcessde",
  url = "jdbc:sqlserver://;servername=CHHUMBUCKET;DatabaseName=MemeFest;trustServerCertificate=true",
  className = "com.microsoft.sqlserver.jdbc.SQLServerDataSource",
  user = "Neutron",
  password = "scoobyDoo24"
)
@Stateless(name = "TopicService")
public class TopicService implements TopicOperations{
    @Resource
    private TimerService timerService;

    @PersistenceContext(unitName = "memeFest", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @EJB
    private CategoryOperations catOps;

    @EJB
    private UserOperations userOperations;

    @EJB
    private PostOperations postOperations;

    private void createPostEntity(Post post) {
    this.entityManager.persist(post);
    }

    public void createScheduledTopic(TopicJSON topic, LocalDateTime postDate){
        ScheduleExpression schedule = new ScheduleExpression()
                                        .year(postDate.getYear())
                                        .month(postDate.getMonthValue())
                                        .dayOfMonth(postDate.getDayOfMonth()).hour(postDate.getHour())
                                        .minute(postDate.getMinute()).second(postDate.getSecond());
        TimerConfig timerConf = new TimerConfig(topic, true);
        timerService.createCalendarTimer(schedule, timerConf);
    }
    
    public void cancelScheduledTopic(TopicJSON topic){
        Collection<Timer> timers = timerService.getTimers();
        for (Timer timerInst : timers) {
            TopicJSON scheduledInst = (TopicJSON) timerInst.getInfo();
            if(topic.getTopicId() != 0 && scheduledInst.getTopicId() == scheduledInst.getTopicId()
            || topic.getTitle() == scheduledInst.getTitle()){
                    timerInst.cancel();
            }
        }
    }

    @Timeout
    public void sendTopic(Timer timer) {
        if(timer.getInfo() instanceof TopicJSON){
            TopicJSON topic = (TopicJSON) timer.getInfo();
            topic.setCreated(LocalDateTime.now());
            createTopic(topic);
        }
    }


    public Set<TopicJSON> searchTopic(TopicJSON topic){
        if (topic == null)
            return null; 
        Query query = this.entityManager.createNamedQuery("Topic.searchTopic");
        query.setParameter("title", topic.getTitle());
        List<Topic> results = query.getResultList();
        Set<TopicJSON> topicSet = results.stream().map(topicEntity ->{
                                    TopicJSON topicJSON = new TopicJSON(topicEntity.getTopic_Id(), null, null, null, null, null);
                                    return getTopicInfo(topicJSON);
        }).collect(Collectors.toSet());
        return topicSet;
    }
  
    public void createTopic(TopicJSON topic) {
        Topic foundTopic = getTopicEntity(topic);
        if (foundTopic != null && foundTopic.getTopic_Id() !=0 && foundTopic.getTopic_Id() !=1)
            return;
        foundTopic.setTitle(topic.getTitle());
        foundTopic.setCreated(Date.from(topic.getCreated().atZone(ZoneId.systemDefault()).toInstant()));
        this.entityManager.persist(foundTopic);
        createTopicCategories(topic);
        removeTopicCategories(topic);
    }
  
    public Set<CategoryJSON> getTopicCategories(TopicJSON topic) {
        Topic topicEntity = getTopicEntity(topic);
        if (topicEntity == null)
            return null; 
        Set<CategoryJSON> topicCategories = new HashSet<>();
        topicCategories = topicEntity.getCategories().stream().map(topicCatEntity ->{
        CategoryJSON catJson = new CategoryJSON(topicCatEntity.getCategory().getCat_Id(), 
                                topicCatEntity.getCategory().getCat_Name(), null, null, null);
            return catJson;
        }).collect(Collectors.toSet());
        return topicCategories;
    }
  
    public void createTopicCategories(TopicJSON topic) {
        Topic foundTopic = getTopicEntity(topic);
        if (foundTopic != null)
            createTopic(topic); 
        Set<CategoryJSON> categories = topic.getCategories();
        for (CategoryJSON category : categories) {
            Category catEntity = catOps.getCategoryEntity(category);
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
            Category catEntity = catOps.getCategoryEntity(cat);
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
  
    public Topic getTopicEntity(TopicJSON topic) throws NoResultException {
        Topic foundTopic = null;
        if ((topic != null) && topic.getTopicId() != 0 && topic.getTopicId() != 1) {
            foundTopic = (Topic)this.entityManager.find(Topic.class, Integer.valueOf(topic.getTopicId()));
            return foundTopic;
        } 
        Query query = this.entityManager.createNamedQuery("Topic.getTopicByTitle");
        query.setParameter("title", topic.getTitle());
        foundTopic = (Topic)query.getSingleResult();
        return foundTopic;
    }
  
    public void createTopicFollowers(TopicJSON topic) {
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
                this.entityManager.persist(newTopicFollower);
            }
            FollowNotificationJSON followNot = new FollowNotificationJSON(0, null, null); 
        }
    }
  
    private TopicFollower getTopicFollower(UserJSON user, TopicJSON topic) {
        TopicFollower foundFollower = null;
        if (user != null && topic != null && user.getUserId() != 0 && topic.getTopicId() != 1) {
            TopicFollowerId followerId = new TopicFollowerId();
            followerId.setUserId(user.getUserId());
            followerId.setTopic_Id(topic.getTopicId());
            foundFollower = (TopicFollower)this.entityManager.find(TopicFollower.class, followerId);
            return foundFollower;
        } 
        if (user != null && topic != null) {
            Topic topicEntity = getTopicEntity(topic);
            User userEntity = this.userOperations.getUserEntity(user);
            if (topicEntity == null || userEntity == null)
                return null;
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
                createTopicFollowers(topic); 
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
        if (topic.getTitle() != null && topic.getTitle().equalsIgnoreCase(foundTopic.getTitle()))
            foundTopic.setTitle(topic.getTitle()); 
        if (topic.getCreated() != null )
            foundTopic.setCreated(Date.from(topic.getCreated().atZone(ZoneId.systemDefault()).toInstant() )); 
        if (topic.getPosts() != null && topic.getPosts().size() > 0)
        for (PostJSON post : topic.getPosts())
            postOperations.editPost(post);  
        if (topic.getCategories() != null && topic.getCategories().size() > 0)
        for (CategoryJSON category : topic.getCategories())
            catOps.editCategory(category);  
        if (topic.getFollowedBy() != null && topic.getFollowedBy().size() > 0)
            editTopicFollowers(topic); 
        this.entityManager.merge(foundTopic);
    }
  
    public void removeTopic(TopicJSON topic) {
        if (topic.isCancelled()) {
            Topic foundTopic = getTopicEntity(topic);
            if (foundTopic != null) {
                for (PostJSON post : topic.getPosts())
                    postOperations.removePost(post); 
                removeTopicFollowers(topic);
                this.entityManager.remove(foundTopic);
            } 
        } 
    }

    public TopicJSON getTopicInfo(TopicJSON topic) {
        Topic topicEntity = getTopicEntity(topic);
        if (topicEntity == null)
            return null; 
        Set<TopicFollower> topicFollowers = topicEntity.getFollowedBy();
        Set<CategoryJSON> categories = (Set<CategoryJSON>)topicEntity.getCategories().stream().map(catInst -> 
                                        new CategoryJSON(catInst.getCategory().getCat_Id(),
                                            catInst.getCategory().getCat_Name(), null, null, null))
                                        .collect(Collectors.toSet());
        Set<UserJSON> users = (Set<UserJSON>)topicFollowers.stream().map(topicFollower -> new UserJSON(topicFollower.getUser()
                                .getUsername())).collect(Collectors.toSet());
        Set<TopicPostJSON> posts = (Set<TopicPostJSON>)topicEntity.getPosts().stream().map(topicPost -> 
                                    new TopicPostJSON(topicPost.getPost_Id(), topicPost.getPost().getComment(), 
                                    LocalDateTime.ofInstant(topicPost.getPost().getCreated().toInstant(), ZoneId.systemDefault()),
                                    topicPost.getPost().getUpvotes(), topicPost.getPost().getDownvotes(), 
                                    new UserJSON(topicPost.getPost().getUser().getUsername()), 
                                    new TopicJSON(topicPost.getTopic().getTopic_Id(), topicPost.getTopic().getTitle(),
                                    LocalDateTime.ofInstant(topicPost.getTopic().getCreated().toInstant(), ZoneId.systemDefault()),
                                   null, null, null))).collect(Collectors.toSet());
        if (topicEntity != null) {
            TopicJSON topicJSON = new TopicJSON(topic.getTopicId(), topic.getTitle(), 
                                    LocalDateTime.ofInstant(topicEntity.getCreated().toInstant(), ZoneId.systemDefault()),
                                    categories, posts, users);
        return topicJSON;
        } 
        return null;
    }
}
