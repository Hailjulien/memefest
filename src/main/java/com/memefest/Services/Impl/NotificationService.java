package com.memefest.Services.Impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.memefest.DataAccess.EventNotification;
import com.memefest.DataAccess.EventNotificationId;
import com.memefest.DataAccess.EventPost;
import com.memefest.DataAccess.EventPostNotification;
import com.memefest.DataAccess.Post;
import com.memefest.DataAccess.PostNotification;
import com.memefest.DataAccess.PostNotificationId;
import com.memefest.DataAccess.Topic;
import com.memefest.DataAccess.TopicFollower;
import com.memefest.DataAccess.TopicPostNotification;
import com.memefest.DataAccess.User;
import com.memefest.DataAccess.UserFollower;
import com.memefest.DataAccess.Event;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.EventOperations;
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
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;


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

    public void createTopicPostNotification(TopicPostNotificationJSON topicPostNotification){
        try{
            getTopicPostNotificationEntity(topicPostNotification);
            Post post = postOps.getPostEntity(topicPostNotification.getPost());
            Topic topic = topicOps.getTopicEntity(topicPostNotification.getTopicPost().getTopic());
            User user = userOps.getUserEntity(topicPostNotification.getUser());
            TopicPostNotification topicPost = new TopicPostNotification();
            topicPost.setPost(post);
            topicPost.setUser(user);
            topicPost.setTopic(topic);
            entityManager.persist(topicPost);
        }
        catch(NoResultException ex){
           return;
        }
    }

    public TopicPostNotification getTopicPostNotificationEntity(TopicPostNotificationJSON topicPostNotification) throws NoResultException{
        if(topicPostNotification != null){
            if(topicPostNotification.getPost() != null){
                PostNotification postNotEntity = getPostNotificationEntity((PostNotificationJSON)topicPostNotification);
                return entityManager.find(TopicPostNotification.class, postNotEntity.getPost_Id());
            }
            else
                throw new NoResultException("No Post Notification found for Topic");
        }
        else
            throw new NoResultException("No Post Notification found for Topic");  
    }

    public PostNotification getPostNotificationEntity(PostNotificationJSON postNotification) throws NoResultException{
        if(postNotification != null){
            if(postNotification.getPost() != null){
                PostJSON post = postOps.getPostInfo(postNotification.getPost());
                PostNotificationId postNotId = new PostNotificationId();
                postNotId.setPost_Id(post.getPostId());
                postNotId.setUserId(post.getUser().getUserId());
                return entityManager.find(PostNotification.class, postNotId);
            }
            else
                throw new NoResultException("No Post Notification found");
        }
        else
            throw new NoResultException("No Post Notification found");
    }

    private void createPostNotification(PostNotificationJSON postNotification){
        try{
            getPostNotificationEntity(postNotification);
            PostNotification postNot = new PostNotification();
            User user = userOps.getUserEntity(postNotification.getPost().getUser());
            Post post = postOps.getPostEntity(postNotification.getPost());
            postNot.setPost(post);
            postNot.setUser(user);
            entityManager.persist(postNot);
        }
        catch(NoResultException ex){
            return;
        }
        
    }

    public void createEventNotification(EventNotificationJSON eventNotification){
        try{
            getEventNotificationEntity(eventNotification);
            User user = userOps.getUserEntity(eventNotification.getUser());
            Event event = eventOps.getEventEntity(eventNotification.getEvent());
            EventNotification eventNot = new EventNotification();
            eventNot.setEvent(event);
            eventNot.setUser(user);
            entityManager.persist(eventNot);
        }
        catch(NoResultException ex){ 
            ex.printStackTrace();
            return;
        }
    }

    public EventNotification getEventNotificationEntity(EventNotificationJSON eventNotification) throws NoResultException{
        if(eventNotification != null && eventNotification.getEvent() != null){
                Event event = eventOps.getEventEntity(eventNotification.getEvent());
                User user = userOps.getUserEntity(eventNotification.getUser());
                EventNotificationId eventNotId = new EventNotificationId();
                eventNotId.setEvent_Id(event.getEvent_Id());
                eventNotId.setUserId(user.getUserId());
                return entityManager.find(EventNotification.class, eventNotId);
            
        }
        else
            throw new NoResultException("No Event Notification found");
    }


    public void createEventPostNotification(EventPostNotificationJSON eventPostNotification){
        try {
            getEventPostNotificationEntity(eventPostNotification);
            EventPost post = postOps.getEventPostEntity(eventPostNotification.getEventPost());
            User user = userOps.getUserEntity(eventPostNotification.getUser());
            EventPostNotification eventPostNot = new EventPostNotification();
            eventPostNot.setEvent(post.getEvent());
            eventPostNot.setPost(post);
            eventPostNot.setUser(user);
            entityManager.persist(eventPostNot);
        } catch (NoResultException ex) {
            return;
        }
    }

    public EventPostNotification getEventPostNotificationEntity(EventPostNotificationJSON eventPostNotification) throws NoResultException{
        if(eventPostNotification != null && eventPostNotification.getEventPost() != null){
            PostNotificationId postNotificationId = new PostNotificationId();
            User user = userOps.getUserEntity(eventPostNotification.getUser());
            EventPost post = postOps.getEventPostEntity(eventPostNotification.getEventPost());
            postNotificationId.setPost_Id(post.getPost_Id());
            postNotificationId.setUserId(user.getUserId());
            return entityManager.find(EventPostNotification.class, postNotificationId);
        }
        else
            throw new NoResultException("No Event Post Notifications found");
    }

    public TopicFollowNotificationJSON getTopicFollowNotificationInfo(TopicFollowNotificationJSON topicFollowNotificationJSON) throws NoResultException{
        Set<String> topicFollowers = getTopicFollowers(topicFollowNotificationJSON);
        topicFollowNotificationJSON.setFollows(topicFollowers);
        return topicFollowNotificationJSON;
    }

    private Set<String> getTopicFollowers(TopicFollowNotificationJSON topicFollowNotification) throws NoResultException{
        if(topicFollowNotification != null && topicFollowNotification.getUser() != null && topicFollowNotification.getTopic() != null){
           Topic topic = topicOps.getTopicEntity(topicFollowNotification.getTopic());
           Set<TopicFollower> topicFollowers = topic.getFollowedBy();
           return topicFollowers.stream().map(topicFollower ->{
            return topicFollower.getFollower().getUsername();
           }).collect(Collectors.toSet());
        }
        else 
            throw new NoResultException("No topic Follows found for User");
    }
    
    public UserFollowNotificationJSON getUserFollowNotificationInfo( UserFollowNotificationJSON userFollowNotification) throws NoResultException{
        Set<String> userfollowers = getUserFollowers(userFollowNotification);
        userFollowNotification.setFollows(userfollowers);
        return userFollowNotification;
    }   

    private  Set<String> getUserFollowers(UserFollowNotificationJSON userFollowNotification) throws NoResultException{
        if(userFollowNotification != null && userFollowNotification.getUser() != null){
            User user = userOps.getUserEntity(userFollowNotification.getUser());
            Set<UserFollower> topicFollowers = user.getUserFollowedBy();
            return topicFollowers.stream().map(topicFollower ->{
             return topicFollower.getFollower().getUsername();
            }).collect(Collectors.toSet());
         }
         else 
             throw new NoResultException("No User Follows found for User");
    }

    public void removeTopicPostNotification(TopicPostNotificationJSON topicPostNotification){
        try{
            TopicPostNotification topicPostNot = getTopicPostNotificationEntity(topicPostNotification);
            entityManager.remove(topicPostNot);
        }
        catch(NoResultException ex){
            return;
        }
    }

    public void removePostNotification(PostNotificationJSON postNotification){
        try{
            PostNotification postNot = getPostNotificationEntity(postNotification);
            entityManager.remove(postNot);
        }
        catch(NoResultException ex){
            return;
        }
    }
    
    public void removeEventPostNotification(EventPostNotificationJSON eventPostNotification){
        try{
            EventPostNotification eventPostNot = getEventPostNotificationEntity(eventPostNotification);
            entityManager.remove(eventPostNot);
        }
        catch(NoResultException ex){
            return;
        }
    }

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
    
    public Set<EventNotificationJSON> getEventNotificationInfo(EventNotificationJSON eventNotification) throws NoResultException{
        if(eventNotification != null && (eventNotification.getUser()!= null || eventNotification.getEvent()!= null)){
            if(eventNotification.getUser() != null){
                Stream<EventNotification> query = entityManager.createNamedQuery("EventNotification.findByEventId", EventNotification.class)
                                        .setParameter("eventId", eventNotification.getUser().getUserId()).getResultStream();
                
                return  query.map(eventNotInst ->{
                                return new EventNotificationJSON(0, LocalDateTime.now(), 
                                                            new EventJSON(eventNotInst.getEvent_Id(), null, null, null, null, null, null, null, null, null, null, null),
                                                                new UserJSON(eventNotInst.getUserId(), null));
                            }).collect(Collectors.toSet());
            }
            else if(eventNotification.getEvent() != null){
                Stream<EventNotification> query = entityManager.createNamedQuery("EventNotification.findByUserId", EventNotification.class)
                .setParameter("userId", eventNotification.getUser().getUserId()).getResultStream();

                return  query.map(eventNotInst ->{
                                return new EventNotificationJSON(0, LocalDateTime.now(), 
                                    new EventJSON(eventNotInst.getEvent_Id(), null, null, null, null, null, null, null, null, null, null, null),
                                        new UserJSON(eventNotInst.getUserId(), null));
                            }).collect(Collectors.toSet());
            }
            else{
                EventNotification eventNotEntity = getEventNotificationEntity(eventNotification);
                return Collections.singleton(new EventNotificationJSON(0, LocalDateTime.now(), 
                    new EventJSON(eventNotEntity.getEvent_Id(), null, null, null, null, null, null, null, null, null, null, null),
                        new UserJSON(eventNotEntity.getUserId(), null)));
            }
        }
        else
            throw new NoResultException("No Event Notification found with given parameters");
    }

    public Set<EventPostNotificationJSON> getEventPostNotificationInfo(EventPostNotificationJSON eventPostNotification) throws NoResultException{
        if(eventPostNotification != null && (eventPostNotification.getUser()!= null || eventPostNotification.getPost()!= null)){
            if(eventPostNotification.getUser() != null){
                Stream<EventPostNotification> query = entityManager.createNamedQuery("EventPostNotification.getEventPostNotificationByEventId", EventPostNotification.class)
                                        .setParameter("eventId", eventPostNotification.getUser().getUserId()).getResultStream();
                
                return  query.map(eventPostNotInst ->{
                                return new EventPostNotificationJSON(0,  
                                                                new EventPostJSON(eventPostNotInst.getPost_Id(), null, null, 0, 0, null, null),
                                                                LocalDateTime.now(),
                                                                new UserJSON(eventPostNotInst.getUserId(), null));
                            }).collect(Collectors.toSet());
            }
            else if(eventPostNotification.getPost() != null){
                Stream<EventPostNotification> query = entityManager.createNamedQuery("EventPostNotification.getEventPostNotificationByUserId", EventPostNotification.class)
                .setParameter("userId", eventPostNotification.getUser().getUserId()).getResultStream();

                return  query.map(eventPostNotInst ->{
                                return new EventPostNotificationJSON(0,  
                                    new EventPostJSON(eventPostNotInst.getPost_Id(), null, null, 0, 0, null, null),
                                    LocalDateTime.now(),    
                                    new UserJSON(eventPostNotInst.getUserId(), null));
                            }).collect(Collectors.toSet());
            }
            else{
                EventPostNotification eventPostNot = getEventPostNotificationEntity(eventPostNotification);
                return Collections.singleton(new EventPostNotificationJSON(0, 
                    new EventPostJSON(eventPostNot.getPost_Id(), null, null, 0, 0,  null, null),
                        LocalDateTime.now(),
                        new UserJSON(eventPostNot.getUserId(), null)));
            }
        }
        else
            throw new NoResultException("No Event Notification found with given parameters");
    }

    public Set<TopicPostNotificationJSON> getTopicPostNotificationInfo(TopicPostNotificationJSON topicPostNotification){
        if(topicPostNotification != null && (topicPostNotification.getUser()!= null || topicPostNotification.getPost()!= null)){
            if(topicPostNotification.getTopicPost() != null){
                Stream<TopicPostNotification> query = entityManager.createNamedQuery("TopicPostNotification.getTopicNotificationByTopicId", TopicPostNotification.class)
                                        .setParameter("topicId", topicPostNotification.getTopicPost().getTopic().getTopicId()).getResultStream();
                
                return  query.map(topicPostNotInst ->{
                                return new TopicPostNotificationJSON(0,  
                                                                new TopicPostJSON(topicPostNotInst.getPost_Id(), null, null, 0, 0, null, null),
                                                                LocalDateTime.now(),
                                                                new UserJSON(topicPostNotInst.getUserId(), null));
                            }).collect(Collectors.toSet());
            }
            else if(topicPostNotification.getUser() != null){
                Stream<TopicPostNotification> query = entityManager.createNamedQuery("TopicPostNotification.getTopicNotificationByUserId", TopicPostNotification.class)
                .setParameter("userId", topicPostNotification.getUser().getUserId()).getResultStream();

                return  query.map(topicPostNotInst ->{
                                return new TopicPostNotificationJSON(0,  
                                    new TopicPostJSON(topicPostNotInst.getPost_Id(), null, null, 0, 0, null, null),
                                    LocalDateTime.now(),    
                                    new UserJSON(topicPostNotInst.getUserId(), null));
                            }).collect(Collectors.toSet());
            }
            else{
                TopicPostNotification topicPostNot = getTopicPostNotificationEntity(topicPostNotification);
                return Collections.singleton(new TopicPostNotificationJSON(0, 
                    new TopicPostJSON(topicPostNot.getPost_Id(), null, null, 0, 0,  null, null),
                        LocalDateTime.now(),
                        new UserJSON(topicPostNot.getUserId(), null)));
            }
        }
        else
            throw new NoResultException("No Event Notification found with given parameters");
    }
}
