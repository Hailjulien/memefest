package com.memefest.Services.Impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.memefest.DataAccess.Event;
import com.memefest.DataAccess.EventNotification;
import com.memefest.DataAccess.EventNotificationId;
import com.memefest.DataAccess.EventPost;
import com.memefest.DataAccess.EventPostNotification;
import com.memefest.DataAccess.EventPostNotificationId;
import com.memefest.DataAccess.FollowNotification;
import com.memefest.DataAccess.FollowNotificationId;
import com.memefest.DataAccess.Post;
import com.memefest.DataAccess.PostNotification;
import com.memefest.DataAccess.PostNotificationId;
import com.memefest.DataAccess.Topic;
import com.memefest.DataAccess.TopicFollowNotification;
import com.memefest.DataAccess.TopicFollowNotificationId;
import com.memefest.DataAccess.TopicFollower;
import com.memefest.DataAccess.TopicPost;
import com.memefest.DataAccess.TopicPostNotification;
import com.memefest.DataAccess.TopicPostNotificationId;
import com.memefest.DataAccess.User;
import com.memefest.DataAccess.UserFollower;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Services.FeedsOperations;
import com.memefest.Services.NotificationOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Services.UserOperations;
import com.memefest.Websockets.JSON.EventNotificationJSON;
import com.memefest.Websockets.JSON.EventPostNotificationJSON;
import com.memefest.Websockets.JSON.PostNotificationJSON;
import com.memefest.Websockets.JSON.TopicFollowNotificationJSON;
import com.memefest.Websockets.JSON.TopicPostNotificationJSON;
import com.memefest.Websockets.JSON.UserFollowNotificationJSON;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;

@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless(name = "NotificationService")
public class NotificationService implements NotificationOperations{
    
    @PersistenceContext(unitName = "memeFest", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @EJB
    private PostOperations postOps;
    
    @EJB
    private UserOperations userOps;

    @EJB 
    private TopicOperations topicOps;

    @EJB
    private EventOperations eventOps;

    @EJB 
    private FeedsOperations feedsOps;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    //throw a custom exception to show object was not created
    private void createTopicPostNotification(TopicPost topicPost, User user)throws NoResultException{
        if(topicPost == null || user == null)
            return;
            TopicPostNotification topicPostNot = new TopicPostNotification();
            topicPostNot.setPost(topicPost.getPost());
            topicPostNot.setUser(user);
            topicPostNot.setTopic(topicPost.getTopic());
            topicPostNot.setSeen(false);
            entityManager.persist(topicPost);

            feedsOps.sendToUsers(topicPostNot);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    //throw a custom exception to show object was not created
    public void createUserFollowNotification(UserFollowNotificationJSON userFollowNot){
        if(userFollowNot == null)
            return;
        User user = userOps.getUserEntity(userFollowNot.getFollower());
        User follower = userOps.getUserEntity(userFollowNot.getUser());
        FollowNotification followNot = new FollowNotification();
        followNot.setFollower_Id(follower.getUserId());
        followNot.setUserId(user.getUserId());
        followNot.setSeen(false);
        entityManager.persist(followNot);
        
        feedsOps.sendToAll(userFollowNot);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createTopicFollowNotification(TopicFollowNotificationJSON topicFollowNot){
        if(topicFollowNot == null)
            return;
        User user = userOps.getUserEntity(topicFollowNot.getUser());
        Topic topic = topicOps.getTopicEntity(topicFollowNot.getTopic());
        TopicFollowNotification followNot = new TopicFollowNotification();
        followNot.setTopic_Id(topic.getTopic_Id());
        followNot.setUserId(user.getUserId());
        followNot.setSeen(false);
        entityManager.persist(followNot);
        
        feedsOps.sendToAll(topicFollowNot);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    //throw a custom exception to show object was not created
    public void editTopicPostNotification(TopicPostNotificationJSON topicPostNot) throws NoResultException{
        if(topicPostNot == null)
            throw new NoResultException("No Notification");
        TopicPostNotification topicPostNotEntity = null;
        try{
            topicPostNotEntity = getTopicPostNotificationEntity(topicPostNot);
            topicPostNotEntity.setSeen(true);
            entityManager.merge(topicPostNotEntity);   
        }
        catch(NoResultException ex){
            if (topicPostNot.isCanceled())
                return;
            User user = userOps.getUserEntity(topicPostNot.getUser());
            TopicPost topicPost = postOps.getTopicPostEntity(topicPostNot.getTopicPost());
            createTopicPostNotification(topicPost, user);
            return;     
        }
        if (topicPostNot.isCanceled()){
            entityManager.remove(topicPostNotEntity);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editUserFollowNotification(UserFollowNotificationJSON userFollowNotification) throws NoResultException{
        if(userFollowNotification == null)
            throw new NoResultException("No Notification found");
        FollowNotification userFollowEntity = null;
        try{
           userFollowEntity = getUserFollowNotificationEntity(userFollowNotification);
           userFollowEntity.setSeen(true);
           entityManager.merge(userFollowEntity);
        }
        catch(NoResultException ex){ 
            if (userFollowNotification.isCanceled())
                return;   
            createUserFollowNotification(userFollowNotification);
            return;
        }
        if (userFollowNotification.isCanceled()){
            entityManager.remove(userFollowEntity);
        }
    }   

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editTopicFollowNotification(TopicFollowNotificationJSON topicFollowNot) throws NoResultException{
        if(topicFollowNot == null)
            throw new NoResultException("No Notification found");
        TopicFollowNotification userFollowEntity = null;
        try {
            userFollowEntity = getTopicFollowNotificationEntity(topicFollowNot);
            userFollowEntity.setSeen(true);
            entityManager.merge(userFollowEntity);
        } catch (Exception e) {
            if (topicFollowNot.isCanceled())
                return;
            createTopicFollowNotification(topicFollowNot);
            editTopicFollowNotification(topicFollowNot);
            return;
        }
        if (topicFollowNot.isCanceled()){
            entityManager.remove(userFollowEntity);
        }    
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public TopicFollowNotification getTopicFollowNotificationEntity(TopicFollowNotificationJSON topicFollowNot) throws NoResultException{
        if(topicFollowNot != null){
            if(topicFollowNot.getUser() != null && topicFollowNot.getTopic() != null){
                User user = userOps.getUserEntity(topicFollowNot.getUser());
                Topic topic = topicOps.getTopicEntity(topicFollowNot.getTopic());
                TopicFollowNotificationId followNotId = new TopicFollowNotificationId();
                followNotId.setUserId(user.getUserId());
                followNotId.setTopic_Id(topic.getTopic_Id());
                TopicFollowNotification topicFollowNotEntity = entityManager.find(TopicFollowNotification.class, followNotId);
                if(topicFollowNotEntity != null)
                    return topicFollowNotEntity;
                else throw new NoResultException();
            }
            else
                throw new NoResultException("No Post Notification found for Topic");
        }
        else
            throw new NoResultException("No Post Notification found for Topic");        
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public FollowNotification getUserFollowNotificationEntity(UserFollowNotificationJSON userFollow) throws NoResultException{
        if(userFollow != null){
            if(userFollow.getFollower() != null && userFollow.getUser() != null){
                User user = userOps.getUserEntity(userFollow.getUser());
                User follower = userOps.getUserEntity(userFollow.getFollower());
                FollowNotificationId followNotId = new FollowNotificationId();
                followNotId.setUserId(user.getUserId());
                followNotId.setFollower_Id(follower.getUserId());
                FollowNotification followNotEntity = entityManager.find(FollowNotification.class, followNotId);
                if(followNotEntity != null)
                    return followNotEntity;
                else throw new NoResultException();
            }
            else
                throw new NoResultException("No Post Notification found for Topic");
        }
        else
            throw new NoResultException("No Post Notification found for Topic");
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public TopicPostNotification getTopicPostNotificationEntity(TopicPostNotificationJSON topicPostNotification) throws NoResultException{
        if(topicPostNotification != null){
            if(topicPostNotification.getPost() != null && topicPostNotification.getUser() != null){
                User user = userOps.getUserEntity(topicPostNotification.getUser());

                TopicPost topicPost = postOps.getTopicPostEntity(topicPostNotification.getTopicPost());
                TopicPostNotificationId postNotId = new TopicPostNotificationId();
                postNotId.setPost_Id(topicPost.getPost_Id());
                postNotId.setUserId(user.getUserId());
                postNotId.setTopic_Id(topicPost.getTopic_Id());
                TopicPostNotification topicNotEntity = entityManager.find(TopicPostNotification.class, postNotId);
                if(topicNotEntity != null)
                    return topicNotEntity;
                else throw new NoResultException();
            }
            else
                throw new NoResultException("No Post Notification found for Topic");
        }
        else
            throw new NoResultException("No Post Notification found for Topic");  
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PostNotification getPostNotificationEntity(PostNotificationJSON postNotification) throws NoResultException{
        if(postNotification != null){
            if(postNotification.getPost() != null && postNotification.getUser() != null){
                PostJSON post = postOps.getPostInfo(postNotification.getPost());
                PostNotificationId postNotId = new PostNotificationId();
                postNotId.setPost_Id(post.getPostId());
                postNotId.setUserId(post.getUser().getUserId());
                PostNotification postEntity = entityManager.find(PostNotification.class, postNotId);
                if(postEntity != null)
                    return postEntity;
                else throw new NoResultException();
            }
            else
                throw new NoResultException("No Post Notification found");
        }
        else
            throw new NoResultException("No Post Notification found");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void createPostNotification(PostNotificationJSON postNotification){
        if(postNotification == null)
            return;
        PostNotification postNot = new PostNotification();
        User user = userOps.getUserEntity(postNotification.getPost().getUser());
        Post post = postOps.getPostEntity(postNotification.getPost());
        postNot.setPost(post);
        postNot.setUser(user);
        postNot.setSeen(false);
        entityManager.persist(postNot);
        
        feedsOps.sendToAll(postNotification);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void editPostNotification(PostNotificationJSON postNot) throws NoResultException{
        if(postNot == null)
            throw new NoResultException("No Notification found");
        PostNotification userFollowEntity = null;    
        try{
            userFollowEntity = getPostNotificationEntity(postNot);
        }
        catch(NoResultException ex){
            if (postNot.isCanceled())
                return;    
            createPostNotification(postNot);
            editPostNotification(postNot);
            return;
        }
        if (postNot.isCanceled()){
            entityManager.remove(userFollowEntity);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    //throw a custom exception to show object was not created
    public void createEventNotification(EventNotificationJSON eventNotification){ 
        User user = userOps.getUserEntity(eventNotification.getUser());
        Event event = eventOps.getEventEntity(eventNotification.getEvent());
        EventNotification eventNot = new EventNotification();
        eventNot.setEvent(event);
        eventNot.setUser(user);
        entityManager.persist(eventNot);

        feedsOps.sendToAll(eventNotification);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    //throw a custom exception to show object was not created
    public void editEventNotification(EventNotificationJSON eventNot) throws NoResultException{
        if(eventNot == null)
            throw new NoResultException("No Event Notification found");
        EventNotification eventNotEntity = null;
        try{
            eventNotEntity = getEventNotificationEntity(eventNot);
            eventNotEntity.setSeen(eventNot.getSeen());
            entityManager.merge(eventNotEntity);
        }
        catch(NoResultException ex){
            if (eventNot.isCanceled())
                return;
            createEventNotification(eventNot);
        }
        if (eventNot.isCanceled()){
            entityManager.remove(eventNotEntity);
        } 
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public EventNotification getEventNotificationEntity(EventNotificationJSON eventNotification) throws NoResultException{
        if(eventNotification != null && eventNotification.getEvent() != null){
                Event event = eventOps.getEventEntity(eventNotification.getEvent());
                User user = userOps.getUserEntity(eventNotification.getUser());
                EventNotificationId eventNotId = new EventNotificationId();
                eventNotId.setEvent_Id(event.getEvent_Id());
                eventNotId.setUserId(user.getUserId());

                EventNotification eventNotEntity = entityManager.find(EventNotification.class, eventNotId);
                if(eventNotEntity == null)
                    throw new NoResultException();
                return eventNotEntity;
            
        }
        else
            throw new NoResultException("No Event Notification found");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    //throw a custom exception to show object was not created
    private void createEventPostNotification(EventPost eventPost, User user){
        if(eventPost  == null || user == null)
            return;
        EventPostNotification eventPostNot = new EventPostNotification();
        eventPostNot.setEvent(eventPost.getEvent());
        eventPostNot.setPost(eventPost.getPost());
        eventPostNot.setUser(user);
        entityManager.persist(eventPostNot);

        feedsOps.sendToAll(eventPostNot);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    //throw a custom exception to show object was not created
    public void editEventPostNotification(EventPostNotificationJSON eventPostNot) throws NoResultException{
        if(eventPostNot == null)
            throw new NoResultException("No Notification");
        EventPostNotification eventPostNotEntity = null;
        try{
            eventPostNotEntity = getEventPostNotificationEntity(eventPostNot); 
            eventPostNotEntity.setSeen(eventPostNot.getSeen());
            entityManager.merge(eventPostNotEntity); 
        }
        catch(NoResultException ex){
            if (eventPostNot.isCanceled())
                return; 
            EventPost eventPost = postOps.getEventPostEntity(eventPostNot.getEventPost());
            User user = userOps.getUserEntity(eventPostNot.getUser());
            createEventPostNotification(eventPost, user);
            editEventPostNotification(eventPostNot);
            return;     
        }
        if (eventPostNot.isCanceled()){
            entityManager.remove(eventPostNotEntity);
        }
    }   

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public EventPostNotification getEventPostNotificationEntity(EventPostNotificationJSON eventPostNotification) throws NoResultException{
        if(eventPostNotification != null && eventPostNotification.getEventPost() != null){
            EventPostNotificationId postNotificationId = new EventPostNotificationId();
            User user = userOps.getUserEntity(eventPostNotification.getUser());
            EventPost post = postOps.getEventPostEntity(eventPostNotification.getEventPost());
            postNotificationId.setPost_Id(post.getPost_Id());
            postNotificationId.setEvent_Id(post.getEvent().getEvent_Id());
            postNotificationId.setUserId(user.getUserId());
            
            EventPostNotification eventPostEntity = entityManager.find(EventPostNotification.class, postNotificationId);
            if(eventPostEntity == null)
                throw new NoResultException();
            return eventPostEntity;
        }
        else
            throw new NoResultException("No Event Post Notifications found");
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Set<TopicFollowNotificationJSON> getTopicFollowNotificationInfo(TopicFollowNotificationJSON topicFollowNotification) throws NoResultException{
        if(topicFollowNotification.getUser() != null || topicFollowNotification.getTopic() != null){
            Set<TopicFollowNotification> topicFollowers = null;
            if(topicFollowNotification.getUser() == null && topicFollowNotification.getTopic() != null){
                Topic topic = topicOps.getTopicEntity(topicFollowNotification.getTopic());
                topicFollowers = this.entityManager.createNamedQuery("TopicFollowNotification.findByTopicId",TopicFollowNotification.class)
                            .setParameter(1, topic.getTopic_Id()).getResultList().stream().collect(Collectors.toSet());
            }
            else if(topicFollowNotification.getUser()!= null && topicFollowNotification.getTopic() == null){
                User user = userOps.getUserEntity(topicFollowNotification.getUser());
                topicFollowers = this.entityManager.createNamedQuery("TopicFollowNotification.findByUserId",TopicFollowNotification.class)
                                 .setParameter(1, user.getUserId()).getResultList().stream().collect(Collectors.toSet());
            }
            else{
                Topic topic = topicOps.getTopicEntity(topicFollowNotification.getTopic());
                User user = userOps.getUserEntity(topicFollowNotification.getUser()); 
                TopicFollowNotificationId topicFollowNotificationId = new TopicFollowNotificationId();
                topicFollowNotificationId.setTopic_Id(topic.getTopic_Id());
                topicFollowNotificationId.setUserId(user.getUserId());
                topicFollowers = Collections.singleton (this.entityManager.find(TopicFollowNotification.class, topicFollowNotificationId));
            }
           return topicFollowers.stream().map(topicFollower ->{
            return new TopicFollowNotificationJSON(0, new TopicJSON(topicFollower.getTopic_Id(), null, null, null, null, null),
                        null,
                        new UserJSON(topicFollower.getUser().getUsername()),false);
           }).collect(Collectors.toSet());
        }
        else 
            throw new NoResultException("No topic Follows found for User");
    }
/*     
    public Set<FollowNotificationJSON> getFollowNotificationInfo(FollowNotificationJSON userFollowNotification) throws NoResultException{
        Set<String> userfollowers = getUserFollowers(userFollowNotification);
        return userfollowers.stream().map(candidate ->{
            return new FollowNotificationJSON(0, null, new UserJSON(candidate), userFollowNotification.getUser());
        }).collect(Collectors.toSet());
    }   
*/
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public  Set<UserFollowNotificationJSON> getUserFollowNotificationInfo(UserFollowNotificationJSON userFollowNotification) throws NoResultException{
        if(userFollowNotification.getUser() != null || userFollowNotification.getFollower() != null){
            Set<FollowNotification> userFollowers = null;
            if(userFollowNotification.getUser() != null && userFollowNotification.getFollower() == null){
                User user = userOps.getUserEntity(userFollowNotification.getUser());
                userFollowers = this.entityManager.createNamedQuery("FollowNotification.findByUserId",FollowNotification.class)
                            .setParameter(1, user.getUserId()).getResultList().stream().collect(Collectors.toSet());
            }
            else if(userFollowNotification.getUser()== null && userFollowNotification.getFollower() != null){
                User user = userOps.getUserEntity(userFollowNotification.getUser());
                userFollowers = this.entityManager.createNamedQuery("FollowNotification.findByFollowerId",FollowNotification.class)
                                 .setParameter(1, user.getUserId()).getResultList().stream().collect(Collectors.toSet());
            }
            else{
                User follower = userOps.getUserEntity(userFollowNotification.getFollower());
                User user = userOps.getUserEntity(userFollowNotification.getUser()); 
                FollowNotificationId userFollowNotificationId = new FollowNotificationId();
                userFollowNotificationId.setFollower_Id(follower.getUserId());
                userFollowNotificationId.setUserId(user.getUserId());
                userFollowers = Collections.singleton (this.entityManager.find(FollowNotification.class, userFollowNotificationId));
            }

            return userFollowers.stream().map(topicFollower ->{
             return new UserFollowNotificationJSON(0, new UserJSON(topicFollower.getUser().getUsername()),
                         null, 
                         new UserJSON(topicFollower.getFollower().getUsername()), userFollowNotification.getSeen());
            }).collect(Collectors.toSet());
         }
         else 
             throw new NoResultException("No User Follows found for User");
    }
/* 
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void removeTopicPostNotification(TopicPostNotificationJSON topicPostNotification){
        try{
            TopicPostNotification topicPostNot = getTopicPostNotificationEntity(topicPostNotification);
            entityManager.remove(topicPostNot);
        }
        catch(NoResultException ex){
            return;
        }
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void removePostNotification(PostNotificationJSON postNotification){
        try{
            PostNotification postNot = getPostNotificationEntity(postNotification);
            entityManager.remove(postNot);
        }
        catch(NoResultException ex){
            return;
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void removeEventPostNotification(EventPostNotificationJSON eventPostNotification){
        try{
            EventPostNotification eventPostNot = getEventPostNotificationEntity(eventPostNotification);
            entityManager.remove(eventPostNot);
        }
        catch(NoResultException ex){
            return;
        }
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void removeEventNotification(EventNotificationJSON eventNotification){
        try{
            EventNotification eventNot = getEventNotificationEntity(eventNotification);
            entityManager.remove(eventNot);
        }
        catch(NoResultException ex){
            return;
        }
    }
    /* 
    public void removeTopicFollowNotification(TopicFollowNotificationJSON topicFollowNotification){
        try{
            Topi
        }
        catch(NoResultException ex){
            return;
        }
    }

    public void removeUserFollowNotification(UserFollowNotificationJSON userFollowNotification){

    }
    */
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Set<EventNotificationJSON> getEventNotificationInfo(EventNotificationJSON eventNotification) throws NoResultException{
        if(eventNotification.getUser() == null && eventNotification.getEvent() != null){
            Stream<EventNotification> query = entityManager.createNamedQuery("EventNotification.findByEventId", EventNotification.class)
                                        .setParameter("eventId", eventNotification.getEvent().getEventID()).getResultStream();
                
                return  query.map(eventNotInst ->{
                                return new EventNotificationJSON(0, LocalDateTime.ofInstant(eventNotInst.getCreated().toInstant(), ZoneId.systemDefault()), 
                                                            new EventJSON(eventNotInst.getEvent_Id(), null, null,null, null, null, null, null, null, null, null, null, null, null,null),
                                                                new UserJSON(eventNotInst.getUserId(), null), eventNotInst.getSeen());
                            }).collect(Collectors.toSet());
        }
        else if(eventNotification.getEvent() == null && eventNotification.getUser() != null){
                Stream<EventNotification> query = entityManager.createNamedQuery("EventNotification.findByUserId", EventNotification.class)
                .setParameter("userId", eventNotification.getUser().getUserId()).getResultStream();

                return  query.map(eventNotInst ->{
                                return new EventNotificationJSON(0, LocalDateTime.ofInstant(eventNotInst.getCreated().toInstant(), ZoneId.systemDefault()), 
                                    new EventJSON(eventNotInst.getEvent_Id(), null, null, null, null,null ,null, null, null, null, null, null, null, null,null),
                                        new UserJSON(eventNotInst.getUserId(), null), eventNotInst.getSeen());
                            }).collect(Collectors.toSet());
        }
        else{
            EventNotification eventNotEntity = getEventNotificationEntity(eventNotification);
                    return Collections.singleton(new EventNotificationJSON(0, LocalDateTime.ofInstant(eventNotEntity.getCreated().toInstant(), ZoneId.systemDefault()), 
                        new EventJSON(eventNotEntity.getEvent_Id(), null, null, null, null,null , null, null, null, null, null, null, null, null, null),
                            new UserJSON(eventNotEntity.getUserId(), null), eventNotEntity.getSeen()));
        }
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Set<EventPostNotificationJSON> getEventPostNotificationInfo(EventPostNotificationJSON eventPostNotification) throws NoResultException{
        if(eventPostNotification != null && (eventPostNotification.getUser()== null && eventPostNotification.getPost() == null && eventPostNotification.getEventPost().getEvent() != null)){
                Stream<EventPostNotification> query = entityManager.createNamedQuery("EventPostNotification.getEventPostNotificationByEventId", EventPostNotification.class)
                                        .setParameter("eventId", eventPostNotification.getEventPost().getEvent().getEventID()).getResultStream();             
                return  query.map(eventPostNotInst ->{
                                return new EventPostNotificationJSON(0,  
                                                                new EventPostJSON(eventPostNotInst.getPost_Id(), null, null, 0, 0, null, null, null ,null),
                                                                 LocalDateTime.ofInstant(eventPostNotInst.getCreated().toInstant(), ZoneId.systemDefault()),
                                                                new UserJSON(eventPostNotInst.getUserId(), null), eventPostNotInst.getSeen());
                            }).collect(Collectors.toSet());
        }
        else if(eventPostNotification.getPost() == null && eventPostNotification.getEventPost().getEvent() == null && eventPostNotification.getUser() != null){
                Stream<EventPostNotification> query = entityManager.createNamedQuery("EventPostNotification.getEventPostNotificationByUserId", EventPostNotification.class)
                .setParameter("userId", eventPostNotification.getUser().getUserId()).getResultStream();

                return  query.map(eventPostNotInst ->{
                                return new EventPostNotificationJSON(0,  
                                    new EventPostJSON(eventPostNotInst.getPost_Id(), null,null, 0, 0, null, 
                                           new EventJSON(eventPostNotInst.getEvent_Id(), null,null,null,null,null,null,null,null,null,null,null,null, null, null), null, null),
                                            LocalDateTime.ofInstant(eventPostNotInst.getCreated().toInstant(), ZoneId.systemDefault()),    
                                    new UserJSON(eventPostNotInst.getUserId(), null),eventPostNotInst.getSeen());
                            }).collect(Collectors.toSet());
        }
        
        else if(eventPostNotification.getPost() != null && eventPostNotification.getEventPost().getEvent() == null && eventPostNotification.getUser() == null){
                Stream<EventPostNotification> query = entityManager.createNamedQuery("EventPostNotification.getEventPostNotificationByPostId", EventPostNotification.class)
                .setParameter("postId", eventPostNotification.getPost().getPostId()).getResultStream();

                return  query.map(eventPostNotInst ->{
                                return new EventPostNotificationJSON(0,  
                                    new EventPostJSON(eventPostNotInst.getPost_Id(), null,null, 0, 0, null, 
                                           new EventJSON(eventPostNotInst.getEvent_Id(), null,null,null,null,null,null,null,null,null,null,null,null, null, null),null, null),
                                             LocalDateTime.ofInstant(eventPostNotInst.getCreated().toInstant(), ZoneId.systemDefault()),    
                                    new UserJSON(eventPostNotInst.getUserId(), null), eventPostNotInst.getSeen());
                            }).collect(Collectors.toSet());
        }
        else if (eventPostNotification.getPost() != null && eventPostNotification.getUser() != null && eventPostNotification.getEventPost().getEvent() == null){
            Stream<EventPostNotification> query =entityManager.createNamedQuery("EventPostNotification.getEventPostNotificationByPostId&UserId", EventPostNotification.class)
                .setParameter("postId", eventPostNotification.getEventPost().getPostId())
                .setParameter("userId", eventPostNotification.getUser().getUserId()).getResultStream();

                return query.map(eventPostNontInst ->{
                        return new EventPostNotificationJSON(0, 
                            new EventPostJSON(eventPostNontInst.getPost_Id(), null, null, 0, 0, null, null, null,null), 
                             LocalDateTime.ofInstant(eventPostNontInst.getCreated().toInstant(), ZoneId.systemDefault()), 
                                new UserJSON(eventPostNontInst.getUserId(), null), eventPostNontInst.getSeen());
                }).collect(Collectors.toSet());
        }
          else if (eventPostNotification.getPost() != null  && eventPostNotification.getUser() == null && eventPostNotification.getEventPost().getEvent() != null){
            Stream<EventPostNotification> query =entityManager.createNamedQuery("EventPostNotification.getEventPostNotificationByPostId&EventId", EventPostNotification.class)
                .setParameter("postId", eventPostNotification.getEventPost().getPostId())
                .setParameter("eventId", eventPostNotification.getEventPost().getEvent().getEventID()).getResultStream();

                return query.map(eventPostNontInst ->{
                        return new EventPostNotificationJSON(0, 
                            new EventPostJSON(eventPostNontInst.getPost_Id(), null, null, 0, 0, null, null, null, null), 
                             LocalDateTime.ofInstant(eventPostNontInst.getCreated().toInstant(), ZoneId.systemDefault()), 
                                new UserJSON(eventPostNontInst.getUserId(), null), eventPostNontInst.getSeen());
                }).collect(Collectors.toSet());
        }
        else if (eventPostNotification.getPost() == null  && eventPostNotification.getUser() != null && eventPostNotification.getEventPost().getEvent() != null){
            Stream<EventPostNotification> query =entityManager.createNamedQuery("EventPostNotification.getEventPostNotificationByUserId&EventId", EventPostNotification.class)
                .setParameter("eventId", eventPostNotification.getEventPost().getEvent().getEventID())
                .setParameter("userId", eventPostNotification.getUser().getUserId()).getResultStream();

                return query.map(eventPostNontInst ->{
                        return new EventPostNotificationJSON(0, 
                            new EventPostJSON(eventPostNontInst.getPost_Id(), null, null, 0, 0, null, null, null, null), 
                             LocalDateTime.ofInstant(eventPostNontInst.getCreated().toInstant(), ZoneId.systemDefault()), 
                                new UserJSON(eventPostNontInst.getUserId(), null), eventPostNontInst.getSeen());
                }).collect(Collectors.toSet());
        }
        else{
            EventPostNotification eventPostNot = getEventPostNotificationEntity(eventPostNotification);
                return Collections.singleton(new EventPostNotificationJSON(0, 
                    new EventPostJSON(eventPostNot.getPost_Id(), null, null, 0, 0,  null, null, null, null),
                         LocalDateTime.ofInstant(eventPostNot.getCreated().toInstant(), ZoneId.systemDefault()),
                        new UserJSON(eventPostNot.getUserId(), null), eventPostNot.getSeen()));
        }
        
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Set<TopicPostNotificationJSON> getTopicPostNotificationInfo(TopicPostNotificationJSON topicPostNotification){
        if(topicPostNotification.getTopicPost().getTopic() != null && topicPostNotification.getUser()== null && topicPostNotification.getPost()== null){
                Stream<TopicPostNotification> query = entityManager.createNamedQuery("TopicPostNotification.getTopicNotificationByTopicId", TopicPostNotification.class)
                                        .setParameter("topicId", topicPostNotification.getTopicPost().getTopic().getTopicId()).getResultStream();
                
                return  query.map(topicPostNotInst ->{
                                return new TopicPostNotificationJSON(0,  
                                                                new TopicPostJSON(topicPostNotInst.getPost_Id(), null,null, 0, 0, null, null, null,null),
                                                                 LocalDateTime.ofInstant(topicPostNotInst.getCreated().toInstant(), ZoneId.systemDefault()),
                                                                new UserJSON(topicPostNotInst.getUserId(), null), topicPostNotInst.getSeen());
                            }).collect(Collectors.toSet());
        }
        else if(topicPostNotification.getUser() != null && topicPostNotification.getTopicPost().getTopic() == null && topicPostNotification.getPost() == null){
                Stream<TopicPostNotification> query = entityManager.createNamedQuery("TopicPostNotification.getTopicNotificationByUserId", TopicPostNotification.class)
                .setParameter("userId", topicPostNotification.getUser().getUserId()).getResultStream();

                return  query.map(topicPostNotInst ->{
                                return new TopicPostNotificationJSON(0,  
                                    new TopicPostJSON(topicPostNotInst.getPost_Id(), null,null, 0, 0, null, null, null, null),
                                    LocalDateTime.ofInstant(topicPostNotInst.getCreated().toInstant(), ZoneId.systemDefault()),    
                                    new UserJSON(topicPostNotInst.getUserId(), null), topicPostNotInst.getSeen());
                            }).collect(Collectors.toSet());
        }
        
        else if(topicPostNotification.getUser() == null && topicPostNotification.getTopicPost().getTopic() == null && topicPostNotification.getPost() != null){
                Stream<TopicPostNotification> query = entityManager.createNamedQuery("TopicPostNotification.getTopicNotificationByPostId", TopicPostNotification.class)
                .setParameter("postId", topicPostNotification.getPost().getPostId()).getResultStream();

                return  query.map(topicPostNotInst ->{
                                return new TopicPostNotificationJSON(0,  
                                    new TopicPostJSON(topicPostNotInst.getPost_Id(), null, null, 0, 0, null, null, null, null),
                                    LocalDateTime.ofInstant(topicPostNotInst.getCreated().toInstant(), ZoneId.systemDefault()),    
                                    new UserJSON(topicPostNotInst.getUserId(), null), topicPostNotInst.getSeen());
                            }).collect(Collectors.toSet());
        }
        else if (topicPostNotification.getPost() == null && topicPostNotification.getUser() != null && topicPostNotification.getTopicPost().getTopic() != null){
            Stream<EventPostNotification> query =entityManager.createNamedQuery("TopicPostNotification.getTopicPostNotificationByPostId&UserId", EventPostNotification.class)
                .setParameter("postId", topicPostNotification.getTopicPost().getPostId())
                .setParameter("userId", topicPostNotification.getUser().getUserId())
                .getResultStream();
                return query.map(topicPostNontInst ->{
                        return new TopicPostNotificationJSON(0, 
                            new TopicPostJSON(topicPostNontInst.getPost_Id(), null, null, 0, 0, null, null, null, null), 
                            LocalDateTime.ofInstant(topicPostNontInst.getCreated().toInstant(), ZoneId.systemDefault()), 
                                new UserJSON(topicPostNontInst.getUserId(), null), topicPostNotification.getSeen());
                }).collect(Collectors.toSet());
        }else if (topicPostNotification.getPost() == null && topicPostNotification.getUser() != null && topicPostNotification.getTopicPost().getTopic() != null){
            Stream<EventPostNotification> query =entityManager.createNamedQuery("TopicPostNotification.getTopicPostNotificationByUserId&TopicId", EventPostNotification.class)
                .setParameter("userId", topicPostNotification.getUser().getUserId())
                .setParameter("topicId", topicPostNotification.getTopicPost().getTopic().getTopicId())
                .getResultStream();
                return query.map(topicPostNontInst ->{
                        return new TopicPostNotificationJSON(0, 
                            new TopicPostJSON(topicPostNontInst.getPost_Id(), null, null, 0, 0, null, null, null, null), 
                            LocalDateTime.ofInstant(topicPostNontInst.getCreated().toInstant(), ZoneId.systemDefault()), 
                                new UserJSON(topicPostNontInst.getUserId(), null), topicPostNontInst.getSeen());
                }).collect(Collectors.toSet());
        }else if (topicPostNotification.getPost() != null && topicPostNotification.getUser() == null && topicPostNotification.getTopicPost().getTopic() != null){
            Stream<EventPostNotification> query =entityManager.createNamedQuery("TopicPostNotification.getTopicPostNotificationByTopicId&PostId", EventPostNotification.class)
                .setParameter("postId", topicPostNotification.getTopicPost().getPostId())
                .setParameter("topicId", topicPostNotification.getTopicPost().getTopic().getTopicId())
                .getResultStream();
                return query.map(topicPostNontInst ->{
                        return new TopicPostNotificationJSON(0, 
                            new TopicPostJSON(topicPostNontInst.getPost_Id(), null, null, 0, 0, null, null, null, null), 
                             LocalDateTime.ofInstant(topicPostNontInst.getCreated().toInstant(), ZoneId.systemDefault()), 
                                new UserJSON(topicPostNontInst.getUserId(), null), topicPostNontInst.getSeen());
                }).collect(Collectors.toSet());
        }

        else{
            TopicPostNotification topicPostNot = getTopicPostNotificationEntity(topicPostNotification);
                return Collections.singleton(new TopicPostNotificationJSON(0, 
                    new TopicPostJSON(topicPostNot.getPost_Id(), null, null, 0, 0,  null, null, null, null),
                         LocalDateTime.ofInstant(topicPostNot.getCreated().toInstant(), ZoneId.systemDefault()),
                        new UserJSON(topicPostNot.getUserId(), null), topicPostNot.getSeen()));
        }
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Set<PostNotificationJSON> getPostNotificationInfo(PostNotificationJSON postNotification){
        if(postNotification != null && (postNotification.getUser()!= null && postNotification.getPost()!= null)){
                Stream<PostNotification> query = entityManager.createNamedQuery("PostNotification.getByUserId", PostNotification.class)
                                        .setParameter("userId", postNotification.getPost().getUser().getUserId()).getResultStream();
                
                return  query.map(topicPostNotInst ->{
                                return new PostNotificationJSON(0,  
                                new PostJSON(topicPostNotInst.getPost_Id(), null, null, 0, 0, null, null, null),
                                                                 LocalDateTime.ofInstant(topicPostNotInst.getCreated().toInstant(), ZoneId.systemDefault()),
                                                                new UserJSON(topicPostNotInst.getUserId(), null), topicPostNotInst.getSeen());
                            }).collect(Collectors.toSet());
        }
        else if(postNotification.getPost() != null && postNotification.getUser() == null){
                Stream<PostNotification> query = entityManager.createNamedQuery("PostNotification.getByPostId", PostNotification.class)
                .setParameter("postId", postNotification.getPost().getPostId()).getResultStream();

                return  query.map(topicPostNotInst ->{
                                return new PostNotificationJSON(0,  
                                    new PostJSON(topicPostNotInst.getPost_Id(), null, null, 0, 0, null, null, null),
                                     LocalDateTime.ofInstant(topicPostNotInst.getCreated().toInstant(), ZoneId.systemDefault()),    
                                    new UserJSON(topicPostNotInst.getUserId(), null), topicPostNotInst.getSeen());
                            }).collect(Collectors.toSet());
        }
        else{
            PostNotification topicPostNot = getPostNotificationEntity(postNotification);
                return Collections.singleton(new PostNotificationJSON(0, 
                    new PostJSON(topicPostNot.getPost_Id(), null, null, 0, 0, null, null, null),
                         LocalDateTime.ofInstant(topicPostNot.getCreated().toInstant(), ZoneId.systemDefault()),
                        new UserJSON(topicPostNot.getUserId(), null), topicPostNot.getSeen()));
            }
    }
}
