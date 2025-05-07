package com.memefest.Services.Impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.memefest.DataAccess.Event;
import com.memefest.DataAccess.EventNotification;
import com.memefest.DataAccess.EventNotificationId;
import com.memefest.DataAccess.EventPost;
import com.memefest.DataAccess.EventPostNotification;
import com.memefest.DataAccess.FollowNotification;
import com.memefest.DataAccess.FollowNotificationId;
import com.memefest.DataAccess.Post;
import com.memefest.DataAccess.PostNotification;
import com.memefest.DataAccess.PostNotificationId;
import com.memefest.DataAccess.Topic;
import com.memefest.DataAccess.TopicFollowNotification;
import com.memefest.DataAccess.TopicFollowNotificationId;
import com.memefest.DataAccess.TopicFollower;
import com.memefest.DataAccess.TopicPostNotification;
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

    @EJB 
    private FeedsOperations feedsOps;

    //throw a custom exception to show object was not created
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
        feedsOps.sendToUsers(topicPostNotification);
    }

    //throw a custom exception to show object was not created
    public void createUserFollowNotification(UserFollowNotificationJSON userFollowNot){
        try{
            getUserFollowNotificationEntity(userFollowNot);
            User user = userOps.getUserEntity(userFollowNot.getFollower());
            User follower = userOps.getUserEntity(userFollowNot.getUser());
            FollowNotification followNot = new FollowNotification();
            followNot.setFollower_Id(follower.getUserId());
            followNot.setUserId(user.getUserId());
            entityManager.persist(followNot);
        }
        catch(NoResultException ex){
           return;
        }
        feedsOps.sendToAll(userFollowNot);
    }

    public void createTopicFollowNotification(TopicFollowNotificationJSON topicFollowNot){
        try{
            getTopicFollowNotificationEntity(topicFollowNot);
            User user = userOps.getUserEntity(topicFollowNot.getUser());
            Topic topic = topicOps.getTopicEntity(topicFollowNot.getTopic());
            TopicFollowNotification followNot = new TopicFollowNotification();
            followNot.setTopic_Id(topic.getTopic_Id());
            followNot.setUserId(user.getUserId());
            entityManager.persist(followNot);
        }
        catch(NoResultException ex){
           return;
        }
        feedsOps.sendToAll(topicFollowNot);
    }

    //throw a custom exception to show object was not created
    public void editTopicPostNotification(TopicPostNotificationJSON topicPostNot) throws NoResultException{
        if(topicPostNot == null)
            throw new NoResultException("No Notification");
        try {
            TopicPostNotification topicPostNotEntity = getTopicPostNotificationEntity(topicPostNot);
            if (topicPostNot.isCanceled()){
                entityManager.remove(topicPostNotEntity);
            } 
        } catch (NoResultException ex) {
            createTopicPostNotification(topicPostNot);     
        }
    }

    public void editUserFollowNotification(UserFollowNotificationJSON userFollowNotification) throws NoResultException{
        if(userFollowNotification == null)
            throw new NoResultException("No Notification found");
        try{
            FollowNotification userFollowEntity = getUserFollowNotificationEntity(userFollowNotification);
            if (userFollowNotification.isCanceled()){
                entityManager.remove(userFollowEntity);
            }
        }
        catch(NoResultException ex){    
            createUserFollowNotification(userFollowNotification);
            return;
        }
    }

    public void editTopicFollowNotification(TopicFollowNotificationJSON topicFollowNot) throws NoResultException{
        if(topicFollowNot == null)
            throw new NoResultException("No Notification found");
        try {
            TopicFollowNotification userFollowEntity = getTopicFollowNotificationEntity(topicFollowNot);
            if (topicFollowNot.isCanceled()){
                entityManager.remove(userFollowEntity);
            }    
        } catch (Exception e) {
            createTopicFollowNotification(topicFollowNot);
        }
    }

    public TopicFollowNotification getTopicFollowNotificationEntity(TopicFollowNotificationJSON topicFollowNot) throws NoResultException{
        if(topicFollowNot != null){
            if(topicFollowNot.getUser() != null && topicFollowNot.getTopic() != null){
                User user = userOps.getUserEntity(topicFollowNot.getUser());
                Topic topic = topicOps.getTopicEntity(topicFollowNot.getTopic());
                TopicFollowNotificationId followNotId = new TopicFollowNotificationId();
                followNotId.setUserId(user.getUserId());
                followNotId.setTopic_Id(topic.getTopic_Id());
                return entityManager.find(TopicFollowNotification.class, followNotId);
            }
            else
                throw new NoResultException("No Post Notification found for Topic");
        }
        else
            throw new NoResultException("No Post Notification found for Topic");        
    }


    public FollowNotification getUserFollowNotificationEntity(UserFollowNotificationJSON userFollow) throws NoResultException{
        if(userFollow != null){
            if(userFollow.getFollower() != null && userFollow.getUser() != null){
                User user = userOps.getUserEntity(userFollow.getUser());
                User follower = userOps.getUserEntity(userFollow.getFollower());
                FollowNotificationId followNotId = new FollowNotificationId();
                followNotId.setUserId(user.getUserId());
                followNotId.setFollower_Id(follower.getUserId());
                return entityManager.find(FollowNotification.class, followNotId);
            }
            else
                throw new NoResultException("No Post Notification found for Topic");
        }
        else
            throw new NoResultException("No Post Notification found for Topic");
    }

    public TopicPostNotification getTopicPostNotificationEntity(TopicPostNotificationJSON topicPostNotification) throws NoResultException{
        if(topicPostNotification != null){
            if(topicPostNotification.getPost() != null && topicPostNotification.getUser() != null){
                User user = userOps.getUserEntity(topicPostNotification.getUser());
                Post post = postOps.getPostEntity(topicPostNotification.getPost());
                PostNotificationId postNotId = new PostNotificationId();
                postNotId.setPost_Id(post.getPost_Id());
                postNotId.setUserId(user.getUserId());
                return entityManager.find(TopicPostNotification.class, postNotId);
            }
            else
                throw new NoResultException("No Post Notification found for Topic");
        }
        else
            throw new NoResultException("No Post Notification found for Topic");  
    }

    public PostNotification getPostNotificationEntity(PostNotificationJSON postNotification) throws NoResultException{
        if(postNotification != null){
            if(postNotification.getPost() != null && postNotification.getUser() != null){
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
        feedsOps.sendToAll(postNotification);
    }

    public void editPostNotification(PostNotificationJSON postNot) throws NoResultException{
        if(postNot == null)
            throw new NoResultException("No Notification found");
        try{
            PostNotification userFollowEntity = getPostNotificationEntity(postNot);
            if (postNot.isCanceled()){
                entityManager.remove(userFollowEntity);
            }
        }
        catch(NoResultException ex){    
            createPostNotification(postNot);
            return;
        }
    }

    //throw a custom exception to show object was not created
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
        feedsOps.sendToAll(eventNotification);
    }

    //throw a custom exception to show object was not created
    public void editEventNotification(EventNotificationJSON eventNot) throws NoResultException{
        if(eventNot == null)
            throw new NoResultException("No Event Notification found");
        try{
            EventNotification eventNotEntity = getEventNotificationEntity(eventNot);
            if (eventNot.isCanceled()){
                entityManager.remove(eventNotEntity);
            }
        }
        catch(NoResultException ex){
            createEventNotification(eventNot);
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

    //throw a custom exception to show object was not created
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
        feedsOps.sendToAll(eventPostNotification);
    }

    //throw a custom exception to show object was not created
    public void editEventPostNotification(EventPostNotificationJSON eventPostNot) throws NoResultException{
        if(eventPostNot == null)
            throw new NoResultException("No Event Post found");
        try {
            EventPostNotification eventPostNotEntity = getEventPostNotificationEntity(eventPostNot);
            if (eventPostNot.isCanceled()){
                entityManager.remove(eventPostNotEntity);
            }
        } catch (NoResultException ex) {
            createEventPostNotification(eventPostNot);
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


    public Set<TopicFollowNotificationJSON> getTopicFollowNotificationInfo(TopicFollowNotificationJSON topicFollowNotification) throws NoResultException{
        if(topicFollowNotification.getUser() != null || topicFollowNotification.getTopic() != null){
           Topic topic = topicOps.getTopicEntity(topicFollowNotification.getTopic());
           Set<TopicFollower> topicFollowers = topic.getFollowedBy();
           return topicFollowers.stream().map(topicFollower ->{
            return new TopicFollowNotificationJSON(0, new TopicJSON(topicFollower.getTopic_Id(), null, null, null, null, null),
                        LocalDateTime.now(),
                        new UserJSON(topicFollower.getFollower().getUsername()));
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
    public  Set<UserFollowNotificationJSON> getUserFollowNotificationInfo(UserFollowNotificationJSON userFollowNotification) throws NoResultException{
        if(userFollowNotification.getUser() != null || userFollowNotification.getFollower() != null){
            User user = userOps.getUserEntity(userFollowNotification.getUser());
            Set<UserFollower> topicFollowers = user.getUserFollowedBy();
            return topicFollowers.stream().map(topicFollower ->{
             return new UserFollowNotificationJSON(0, new UserJSON(topicFollower.getUser().getUsername()),
                         LocalDateTime.now(), 
                         new UserJSON(topicFollower.getFollower().getUsername()));
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

    public Set<PostNotificationJSON> getPostNotificationInfo(PostNotificationJSON postNotification){
        if(postNotification != null && (postNotification.getUser()!= null || postNotification.getPost()!= null)){
            if(postNotification.getUser() != null){
                Stream<PostNotification> query = entityManager.createNamedQuery("PostNotification.getByUserId", PostNotification.class)
                                        .setParameter("userId", postNotification.getPost().getUser().getUserId()).getResultStream();
                
                return  query.map(topicPostNotInst ->{
                                return new PostNotificationJSON(0,  
                                new PostJSON(topicPostNotInst.getPost_Id(), null, null, 0, 0, null),
                                                                LocalDateTime.now(),
                                                                new UserJSON(topicPostNotInst.getUserId(), null));
                            }).collect(Collectors.toSet());
            }
            else if(postNotification.getPost() != null){
                Stream<PostNotification> query = entityManager.createNamedQuery("PostNotification.getByPostId", PostNotification.class)
                .setParameter("postId", postNotification.getPost().getPostId()).getResultStream();

                return  query.map(topicPostNotInst ->{
                                return new PostNotificationJSON(0,  
                                    new PostJSON(topicPostNotInst.getPost_Id(), null, null, 0, 0, null),
                                    LocalDateTime.now(),    
                                    new UserJSON(topicPostNotInst.getUserId(), null));
                            }).collect(Collectors.toSet());
            }
            else{
                PostNotification topicPostNot = getPostNotificationEntity(postNotification);
                return Collections.singleton(new PostNotificationJSON(0, 
                    new PostJSON(topicPostNot.getPost_Id(), null, null, 0, 0, null),
                        LocalDateTime.now(),
                        new UserJSON(topicPostNot.getUserId(), null)));
            }
        }
        else
            throw new NoResultException("No Event Notification found with given parameters");
    }
}
