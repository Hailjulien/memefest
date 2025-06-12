package com.memefest.Websockets.MessageHandlers;
import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.CategoryOperations;
import com.memefest.Services.EventOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Services.UserOperations;
import com.memefest.Websockets.JSON.EditCategoryJSON;
import com.memefest.Websockets.JSON.EditEventJSON;
import com.memefest.Websockets.JSON.EditEventPostJSON;
import com.memefest.Websockets.JSON.EditPostJSON;
import com.memefest.Websockets.JSON.EditPostWithReplyJSON;
import com.memefest.Websockets.JSON.EditResultCategoryJSON;
import com.memefest.Websockets.JSON.EditResultEventJSON;
import com.memefest.Websockets.JSON.EditResultEventPostJSON;
import com.memefest.Websockets.JSON.EditResultPostJSON;
import com.memefest.Websockets.JSON.EditResultPostWithReplyJSON;
import com.memefest.Websockets.JSON.EditResultTopicJSON;
import com.memefest.Websockets.JSON.EditResultUserJSON;
import com.memefest.Websockets.JSON.EditTopicJSON;
import com.memefest.Websockets.JSON.EditUserJSON;
import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;

public class EditUserMessageHandler implements MessageHandler.Whole<Object>{

   private UserOperations userOps;
   private TopicOperations topicOps;
   private CategoryOperations catOps;
   private PostOperations postOps;
   private EventOperations eventOps;

   private Session session;

   public EditUserMessageHandler(UserOperations userOps, 
                                    TopicOperations topicOps,
                                        CategoryOperations catOps,
                                            PostOperations postOps,
                                                EventOperations eventOps,
                                                    Session session){
       this.userOps = userOps;
       this.session = session;
       this.topicOps = topicOps;
       this.catOps = catOps;
       this.postOps = postOps;
       this.eventOps = eventOps;
   }

   //add customisation filter according to users tastes here
    @Override
    public  void onMessage(Object userEdit){
        if(userEdit instanceof EditUserJSON){
            editUsers((EditUserJSON)userEdit);
        }
        else if(userEdit instanceof EditTopicJSON){
            editTopics((EditTopicJSON)userEdit);
        }
        else if(userEdit instanceof EditCategoryJSON)
            editCategory((EditCategoryJSON)userEdit);
        else if(userEdit instanceof EditPostWithReplyJSON)
            editPostWithReply((EditPostWithReplyJSON)userEdit);
        else if(userEdit instanceof EditPostJSON)
            editPost((EditPostJSON)userEdit);
        else if(userEdit instanceof EditEventPostJSON)
            editEventPost((EditEventPostJSON)userEdit);
        else if(userEdit instanceof EditEventJSON)
            editEvent((EditEventJSON)userEdit);
   }


   private void editUsers(EditUserJSON userEdit){
        Set<UserJSON> users = userEdit.getUsers();
        EditResultUserJSON successEdits = new EditResultUserJSON(200, "Success",null);
        EditResultUserJSON failureEdits = new EditResultUserJSON(203, "Could not edit", null);

        for(UserJSON user : users){
            try{
                userOps.editUser((UserJSON)user);
                UserJSON userEntity = userOps.getUserInfo((UserJSON)user);
                Set<UserJSON> resUsers = successEdits.getUsers();
                if(resUsers == null)
                    resUsers = new HashSet<UserJSON>();
                resUsers.add(userEntity);
                successEdits.setUsers(resUsers);
            }
            catch(EJBException ex){
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<UserJSON> resUsers = failureEdits.getUsers();
                if(resUsers == null)
                    resUsers = new HashSet<UserJSON>();
                resUsers.add(user);
                failureEdits.setUsers(resUsers);
            }
                    
        }
        if(successEdits.getUsers() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getUsers() != null)
            session.getAsyncRemote().sendObject(failureEdits);
            //send updated post to user who initiated the edit operation        
   }
   
   private void editTopics(EditTopicJSON editTopic){
        Set<TopicJSON> topics = editTopic.getTopics();
        EditResultTopicJSON resultTopic = new EditResultTopicJSON(null,200, "success");
        EditResultTopicJSON notEditableTopic = new EditResultTopicJSON(null,203, "Could not edit");
        for(TopicJSON topic : topics){
            try{
                topicOps.editTopic(topic);
                TopicJSON topicEntity = topicOps.getTopicInfo(topic);
                Set<TopicJSON> resCats = resultTopic.getTopics();
                if(resCats == null)
                    resCats = new HashSet<TopicJSON>();
                resCats.add(topicEntity);
                resultTopic.setTopics(resCats);
            } 
            catch(EJBException | NoResultException ex){                    
                notEditableTopic.setResultMessage(notEditableTopic.getResultMessage() + "," + ex.getMessage());
                Set<TopicJSON> resCats = notEditableTopic.getTopics();
                if(resCats == null)
                    resCats = new HashSet<TopicJSON>();
                resCats.add(topic);
                notEditableTopic.setTopics(resCats);
            }           
        }
        if(resultTopic.getTopics() != null)
            session.getAsyncRemote().sendObject(resultTopic);
        if(notEditableTopic.getTopics() != null)
            session.getAsyncRemote().sendObject(notEditableTopic);                   
   }

   private void editCategory(EditCategoryJSON catEdit){
        Set<CategoryJSON> categories = catEdit.getCategories();
        
        EditResultCategoryJSON successEdits = new EditResultCategoryJSON(null, 200, "Success");
        EditResultCategoryJSON failureEdits = new EditResultCategoryJSON(null, 203, "Could not edit");

        for(CategoryJSON category : categories){
            try{
                catOps.editCategory((CategoryJSON)category);
                CategoryJSON catEntity = catOps.getCategoryInfo((CategoryJSON)category);
                Set<CategoryJSON> resCats = successEdits.getCategories();
                if(resCats == null)
                    resCats = new HashSet<CategoryJSON>();
                resCats.add(catEntity);
                successEdits.setCategories(resCats);
            }
            catch(EJBException | NoResultException ex){
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<CategoryJSON> resCats = failureEdits.getCategories();
                if(resCats == null)
                    resCats = new HashSet<CategoryJSON>();
                resCats.add(category);
                failureEdits.setCategories(resCats);
            }
        }
        if(successEdits.getCategories() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getCategories() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }

   private void editPostWithReply(EditPostWithReplyJSON postWithReplyEdit){
        Set<PostWithReplyJSON> posts = postWithReplyEdit.getPostsWithReplys();
        EditResultPostWithReplyJSON successEdits = new EditResultPostWithReplyJSON(200 , "success", null);
        EditResultPostWithReplyJSON failureEdits = new EditResultPostWithReplyJSON(203, "Could not edit", null);
        for(PostWithReplyJSON post : posts){
            try{
                postOps.editPostWithReply(post);
                PostWithReplyJSON postEntity = postOps.getPostWithReplyInfo(post);
                Set<PostWithReplyJSON> resCats = successEdits.getPostWithReplies();
                if(resCats == null)
                    resCats = new HashSet<PostWithReplyJSON>();
                resCats.add(postEntity);
                successEdits.setPostWithReplies(resCats);
            }
            catch(EJBException | NoResultException ex){                    
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<PostWithReplyJSON> resCats = failureEdits.getPostWithReplies();
                if(resCats == null)
                    resCats = new HashSet<PostWithReplyJSON>();
                resCats.add(post);
                failureEdits.setPostWithReplies(resCats);
            }
                    
        }
        if(successEdits.getPostWithReplies() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getPostWithReplies() != null)
            session.getAsyncRemote().sendObject(failureEdits);                   
   }

   private void editPost(EditPostJSON postEdit){
        Set<PostJSON> posts = postEdit.getPosts();
        EditResultPostJSON successEdits = new EditResultPostJSON(null ,200, "success");
        EditResultPostJSON failureEdits = new EditResultPostJSON(null, 203, "Could not edit");
        for(PostJSON post : posts){
            try{
                postOps.editPost((PostJSON)post);
                PostJSON postEntity = postOps.getPostInfo(post);
                Set<PostJSON> resCats = successEdits.getPosts();
                if(resCats == null)
                    resCats = new HashSet<PostJSON>();
                resCats.add(postEntity);
                successEdits.setPosts(resCats);
            } 
            catch(EJBException  | NoResultException ex){                    
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<PostJSON> resCats = failureEdits.getPosts();
                if(resCats == null)
                    resCats = new HashSet<PostJSON>();
                resCats.add(post);
                failureEdits.setPosts(resCats);
            }            
        }
        if(successEdits.getPosts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getPosts() != null)
            session.getAsyncRemote().sendObject(failureEdits);                   
   }

   private void editEventPost(EditEventPostJSON eventEdit){
        Set<EventPostJSON> eventPosts = eventEdit.getEventPosts();
        EditResultEventPostJSON successEdits = new EditResultEventPostJSON(200, "Success", null);
        EditResultEventPostJSON failureEdits = new EditResultEventPostJSON(203, "Could not edit", null);
        for(EventPostJSON eventPost : eventPosts){
            try{
                postOps.editEventPost((EventPostJSON)eventPost);
                    EventPostJSON eventPostEntity = postOps.getEventPostInfo(eventPost);
                    Set<EventPostJSON> resCats = successEdits.getEventPosts();
                    if(resCats == null)
                        resCats = new HashSet<EventPostJSON>();
                    resCats.add(eventPostEntity);
                    successEdits.setEventPosts(resCats);
            }
            catch(EJBException | NoResultException ex){                    
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<EventPostJSON> resCats = failureEdits.getEventPosts();
                if(resCats == null)
                    resCats = new HashSet<EventPostJSON>();
                resCats.add(eventPost);
                failureEdits.setEventPosts(resCats);
            }
                    
        }
        if(successEdits.getEventPosts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getEventPosts() != null)
            session.getAsyncRemote().sendObject(failureEdits);           
   }

   public void editEvent(EditEventJSON eventEdit){ 
        Set<EventJSON> events = eventEdit.getEvents();
        
        EditResultEventJSON successEdits = new EditResultEventJSON(null, 200, "Success");
        EditResultEventJSON failureEdits = new EditResultEventJSON(null, 203, "Could not edit");

        for(EventJSON event : events){
            try{
                eventOps.editEvent((EventJSON)event);
                EventJSON eventEntity = eventOps.getEventInfo((EventJSON)event);
                Set<EventJSON> resEvent = successEdits.getEvents();
                if(resEvent == null)
                    resEvent = new HashSet<EventJSON>();
                resEvent.add(eventEntity);
                successEdits.setEvents(resEvent);
            } 
            catch (NoResultException | EJBException e) {
                Set<EventJSON> resEvent = failureEdits.getEvents();
                if(resEvent == null)
                    resEvent = new HashSet<EventJSON>();
                resEvent.add(event);
                failureEdits.setEvents(resEvent);
            }       
        }
        
        if(successEdits.getEvents() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getEvents() != null)
            session.getAsyncRemote().sendObject(failureEdits);
               
   }

}