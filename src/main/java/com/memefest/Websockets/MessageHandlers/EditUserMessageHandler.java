package com.memefest.Websockets.MessageHandlers;
import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import com.memefest.DataAccess.JSON.RepostJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.CategoryOperations;
import com.memefest.Services.EventOperations;
import com.memefest.Services.NotificationOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Services.UserOperations;
import com.memefest.Websockets.Encoders.EditPostNotificationEncoder;
import com.memefest.Websockets.JSON.EditCategoryJSON;
import com.memefest.Websockets.JSON.EditEventJSON;
import com.memefest.Websockets.JSON.EditEventNotificationJSON;
import com.memefest.Websockets.JSON.EditEventPostJSON;
import com.memefest.Websockets.JSON.EditEventPostNotificationJSON;
import com.memefest.Websockets.JSON.EditPostJSON;
import com.memefest.Websockets.JSON.EditPostNotificationJSON;
import com.memefest.Websockets.JSON.EditPostWithReplyJSON;
import com.memefest.Websockets.JSON.EditRepostJSON;
import com.memefest.Websockets.JSON.EditResultCategoryJSON;
import com.memefest.Websockets.JSON.EditResultEventJSON;
import com.memefest.Websockets.JSON.EditResultEventNotificationJSON;
import com.memefest.Websockets.JSON.EditResultEventPostJSON;
import com.memefest.Websockets.JSON.EditResultEventPostNotificationJSON;
import com.memefest.Websockets.JSON.EditResultPostJSON;
import com.memefest.Websockets.JSON.EditResultPostNotificationJSON;
import com.memefest.Websockets.JSON.EditResultPostWithReplyJSON;
import com.memefest.Websockets.JSON.EditResultRepostJSON;
import com.memefest.Websockets.JSON.EditResultTopicFollowNotificationJSON;
import com.memefest.Websockets.JSON.EditResultTopicJSON;
import com.memefest.Websockets.JSON.EditResultTopicPostJSON;
import com.memefest.Websockets.JSON.EditResultTopicPostNotificationJSON;
import com.memefest.Websockets.JSON.EditResultUserFollowNotificationJSON;
import com.memefest.Websockets.JSON.EditResultUserJSON;
import com.memefest.Websockets.JSON.EditTopicFollowNotificationJSON;
import com.memefest.Websockets.JSON.EditTopicJSON;
import com.memefest.Websockets.JSON.EditTopicPostJSON;
import com.memefest.Websockets.JSON.EditTopicPostNotificationJSON;
import com.memefest.Websockets.JSON.EditUserFollowNotificationJSON;
import com.memefest.Websockets.JSON.EditUserJSON;
import com.memefest.Websockets.JSON.EventNotificationJSON;
import com.memefest.Websockets.JSON.EventPostNotificationJSON;
import com.memefest.Websockets.JSON.GetCategoryJSON;
import com.memefest.Websockets.JSON.GetEventJSON;
import com.memefest.Websockets.JSON.GetEventNotificationJSON;
import com.memefest.Websockets.JSON.GetEventPostJSON;
import com.memefest.Websockets.JSON.GetEventPostNotificationJSON;
import com.memefest.Websockets.JSON.GetPostJSON;
import com.memefest.Websockets.JSON.GetPostNotificationJSON;
import com.memefest.Websockets.JSON.GetPostReplysJSON;
import com.memefest.Websockets.JSON.GetRepostJSON;
import com.memefest.Websockets.JSON.GetResultCategoryJSON;
import com.memefest.Websockets.JSON.GetResultEventJSON;
import com.memefest.Websockets.JSON.GetResultEventNotificationJSON;
import com.memefest.Websockets.JSON.GetResultEventPostJSON;
import com.memefest.Websockets.JSON.GetResultEventPostNotificationJSON;
import com.memefest.Websockets.JSON.GetResultPostJSON;
import com.memefest.Websockets.JSON.GetResultPostNotificationJSON;
import com.memefest.Websockets.JSON.GetResultPostWithReplyJSON;
import com.memefest.Websockets.JSON.GetResultRepostJSON;
import com.memefest.Websockets.JSON.GetResultTopicFollowNotificationJSON;
import com.memefest.Websockets.JSON.GetResultTopicJSON;
import com.memefest.Websockets.JSON.GetResultTopicPostJSON;
import com.memefest.Websockets.JSON.GetResultTopicPostNotificationJSON;
import com.memefest.Websockets.JSON.GetResultUserFollowNotificationJSON;
import com.memefest.Websockets.JSON.GetTopicFollowNotificationJSON;
import com.memefest.Websockets.JSON.GetTopicJSON;
import com.memefest.Websockets.JSON.GetTopicPostJSON;
import com.memefest.Websockets.JSON.GetTopicPostNotificationJSON;
import com.memefest.Websockets.JSON.GetUserFollowNotificationJSON;
import com.memefest.Websockets.JSON.PostNotificationJSON;
import com.memefest.Websockets.JSON.SearchCategoryJSON;
import com.memefest.Websockets.JSON.SearchEventJSON;
import com.memefest.Websockets.JSON.SearchPostJSON;
import com.memefest.Websockets.JSON.SearchResultCategoryJSON;
import com.memefest.Websockets.JSON.SearchResultEventJSON;
import com.memefest.Websockets.JSON.SearchResultPostJSON;
import com.memefest.Websockets.JSON.SearchResultTopicJSON;
import com.memefest.Websockets.JSON.SearchTopicJSON;
import com.memefest.Websockets.JSON.TopicFollowNotificationJSON;
import com.memefest.Websockets.JSON.TopicPostNotificationJSON;
import com.memefest.Websockets.JSON.UserFollowNotificationJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.OnMessage;
import jakarta.websocket.PongMessage;

public class EditUserMessageHandler implements MessageHandler.Whole<Object>{

   private UserOperations userOps;
   private TopicOperations topicOps;
   private CategoryOperations catOps;
   private PostOperations postOps;
   private EventOperations eventOps;
   private NotificationOperations notOps;

   private Session session;

   public EditUserMessageHandler(UserOperations userOps, 
                                    TopicOperations topicOps,
                                        CategoryOperations catOps,
                                            PostOperations postOps,
                                                EventOperations eventOps,
                                                NotificationOperations notOps,
                                                    Session session){
       this.userOps = userOps;
       this.session = session;
       this.topicOps = topicOps;
       this.catOps = catOps;
       this.postOps = postOps;
       this.eventOps = eventOps;
       this.notOps = notOps;
   }

   //add customisation filter according to users tastes here
    @Override
    public  void onMessage(Object userEdit){

        if(userEdit instanceof EditTopicJSON){
            editTopics((EditTopicJSON)userEdit);
        }
        
        if (userEdit instanceof GetTopicJSON){
            getTopics((GetTopicJSON)userEdit);
        }
        if(userEdit instanceof SearchTopicJSON){
            searchTopic((SearchTopicJSON)userEdit);
        }

        
        if(userEdit instanceof EditCategoryJSON){
            editCategory((EditCategoryJSON)userEdit);
        }
        if(userEdit instanceof GetCategoryJSON){
            getCategory((GetCategoryJSON)userEdit);
        }
        if(userEdit instanceof SearchCategoryJSON){
            searchCategory((SearchCategoryJSON)userEdit);
        }

        if(userEdit instanceof EditPostWithReplyJSON)
            editPostWithReply((EditPostWithReplyJSON)userEdit);
        if(userEdit instanceof GetPostReplysJSON)
            getPostWithReply((GetPostReplysJSON)userEdit);

        if(userEdit instanceof EditPostJSON)
            editPost((EditPostJSON)userEdit);
        if(userEdit instanceof GetPostJSON)
            getPost((GetPostJSON)userEdit);
        if(userEdit instanceof SearchPostJSON)
            searchPost((SearchPostJSON)userEdit);
            
        if(userEdit instanceof GetTopicPostJSON)
            getTopicPost((GetTopicPostJSON)userEdit);
        if(userEdit instanceof EditTopicPostJSON)
            editTopicPost((EditTopicPostJSON)userEdit);


        if(userEdit instanceof EditEventPostJSON)
            editEventPost((EditEventPostJSON)userEdit);
        if(userEdit instanceof GetEventPostJSON)
            getEventPost((GetEventPostJSON)userEdit);

        
        if(userEdit instanceof EditRepostJSON)
            editRepost((EditRepostJSON)userEdit);
        if(userEdit instanceof GetRepostJSON)
            getRepost((GetRepostJSON)userEdit);

        if(userEdit instanceof EditEventJSON)
            editEvent((EditEventJSON)userEdit);        
        if(userEdit instanceof GetEventJSON)
            getEvent((GetEventJSON)userEdit);
        if(userEdit instanceof SearchEventJSON)
            searchEvent((SearchEventJSON) userEdit);


        if(userEdit instanceof EditEventNotificationJSON)
            editEventNotification((EditEventNotificationJSON)userEdit);
        if(userEdit instanceof GetEventNotificationJSON)
            getEventNotification((GetEventNotificationJSON)userEdit);

        if(userEdit instanceof EditEventPostNotificationJSON)
            editEventPostNotification((EditEventPostNotificationJSON)userEdit);
        if(userEdit instanceof GetEventPostNotificationJSON)
            getEventPostNotification((GetEventPostNotificationJSON)userEdit);        

        if(userEdit instanceof EditTopicPostNotificationJSON)
            editTopicPostNotification((EditTopicPostNotificationJSON)userEdit);
        if(userEdit instanceof GetTopicPostNotificationJSON)
            getTopicPostNotification((GetTopicPostNotificationJSON)userEdit); 

        if(userEdit instanceof EditPostNotificationJSON)
            editPostNotification((EditPostNotificationJSON)userEdit);
        if(userEdit instanceof GetPostNotificationJSON)
            getPostNotification((GetPostNotificationJSON)userEdit);

        if(userEdit instanceof EditTopicFollowNotificationJSON)
            editTopicFollowNotification((EditTopicFollowNotificationJSON)userEdit);
        if(userEdit instanceof GetTopicFollowNotificationJSON)
            getTopicFollowNotification((GetTopicFollowNotificationJSON)userEdit);
        

        if(userEdit instanceof EditUserFollowNotificationJSON)
            editUserFollowNotification((EditUserFollowNotificationJSON)userEdit);
        if(userEdit instanceof GetUserFollowNotificationJSON)
            getUserFollowNotification((GetUserFollowNotificationJSON)userEdit);
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
                Set<TopicJSON> resCats = new HashSet<TopicJSON>();
                if(notEditableTopic.getTopics() != null){
                    resCats.addAll(notEditableTopic.getTopics());
                }
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
   /* 
   private void editEvent(EditEventJSON eventEdit){ 
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
    */  
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

    private void getEvent(GetEventJSON getEvent){
        GetResultEventJSON successEdits = new GetResultEventJSON(200, "Success",null);
        GetResultEventJSON failureEdits = new GetResultEventJSON(203, "Could not edit", null);
           for(EventJSON event : getEvent.getEvents()){
                try{
                    EventJSON eventEntity = eventOps.getEventInfo(event);
                    if(eventEntity!= null){
                        Set<EventJSON> eventCats = successEdits.getEventResults();
                        if(eventCats == null)
                            eventCats = new HashSet<EventJSON>();
                        eventCats.add(eventEntity);
                        successEdits.setEventResults(eventCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<EventJSON> eventCats = failureEdits.getEventResults();
                    if(eventCats == null)
                        eventCats = new HashSet<EventJSON>();
                    eventCats.add(event);
                    failureEdits.setEventResults(eventCats);
                }
        }
        if(successEdits.getEventResults() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getEventResults() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }

    private void searchEvent(SearchEventJSON searchCommand){
        Set<EventJSON> events = eventOps.searchEvents(searchCommand.getEvent());
        //add customisation filter according to users tastes here
        SearchResultEventJSON result = new SearchResultEventJSON(null, 0, null);
        try{
            if(events.size() == 0){
                result.setResultCode(204);
                result.setResultMessage("No results");
            }
            else{    
                result.setResultId(200);
                result.setResultMessage("Success");
                result.setEvents(events);
            }
            session.getAsyncRemote().sendObject(result);
        }
        catch (NoResultException | EJBException ex){
            result.setResultCode(204);
            result.setResultMessage("No results");
            session.getAsyncRemote().sendObject(result);
        }
    }

    private void getTopics(GetTopicJSON getTopic){
        GetResultTopicJSON successEdits = new GetResultTopicJSON(200, "Success",null);
        GetResultTopicJSON failureEdits = new GetResultTopicJSON(203, "Could not edit", null);

           for(TopicJSON topic : getTopic.getTopics()){
                try{
                    TopicJSON topicEntity = topicOps.getTopicInfo(topic);
                    if(topicEntity!= null){
                        Set<TopicJSON> topicCats = successEdits.getTopics();
                        if(topicCats== null)
                            topicCats = new HashSet<TopicJSON>();
                        topicCats.add(topicEntity);
                        successEdits.setTopics(topicCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<TopicJSON> topicCats = failureEdits.getTopics();
                    if(topicCats == null)
                        topicCats = new HashSet<TopicJSON>();
                    topicCats.add(topic);
                    failureEdits.setTopics(topicCats);
                } 
                catch (NoResultException e) { 
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<TopicJSON> topicCats = failureEdits.getTopics();
                    if(topicCats == null)
                        topicCats = new HashSet<TopicJSON>();
                    topicCats.add(topic);
                    failureEdits.setTopics(topicCats);
                }
        }
        if(successEdits.getTopics() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getTopics() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }
    
    private void searchTopic(SearchTopicJSON searchCommand){
        TopicJSON topic = searchCommand.getTopic();
        Set<TopicJSON> topics = topicOps.searchTopic(topic);
        //add customisation filter according to users tastes here
        SearchResultTopicJSON result = new SearchResultTopicJSON(null, 0, null);
        try{
            if(topics.size() == 0){
                result.setResultCode(204);
                result.setResultMessage("No results");
            }
            else{    
                result.setResultId(200);
                result.setResultMessage("Success");
                result.setTopics(topics);
            }
            session.getAsyncRemote().sendObject(result);
        }
        catch (NoResultException | EJBException ex){
            result.setResultCode(204);
            result.setResultMessage("No results");
            session.getAsyncRemote().sendObject(result);
        }
   }
   
   private void getCategory(GetCategoryJSON getCat){
        GetResultCategoryJSON successEdits = new GetResultCategoryJSON(200, "Success",null);
        GetResultCategoryJSON failureEdits = new GetResultCategoryJSON(203, "Could not edit", null);

           for(CategoryJSON categ : getCat.getCategories()){
                try{
                    CategoryJSON catEntity = catOps.getCategoryInfo(categ);
                    if(catEntity!= null){
                        Set<CategoryJSON> resCats = successEdits.getCategories();
                        resCats.add(catEntity);
                        successEdits.setCategories(resCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<CategoryJSON> resCats = failureEdits.getCategories();
                    if(resCats == null)
                        resCats = new HashSet<CategoryJSON>();
                    resCats.add(categ);
                    failureEdits.setCategories(resCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<CategoryJSON> resCats = failureEdits.getCategories();
                    if(resCats == null)
                        resCats = new HashSet<CategoryJSON>();
                    resCats.add(categ);
                    failureEdits.setCategories(resCats);
                }
        }
        if(successEdits.getCategories() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getCategories() != null)
            session.getAsyncRemote().sendObject(failureEdits);
        //send updated post to user who 
    }


    private void searchCategory(SearchCategoryJSON searchCommand){
        Set<CategoryJSON> categories = catOps.searchCategory(searchCommand.getCategory());
        //add customisation filter according to users tastes here
        SearchResultCategoryJSON result = new SearchResultCategoryJSON(null, 0, null);
        try{
            if(categories.size() == 0){
                result.setResultCode(204);
                result.setResultMessage("No results");
            }
            else{    
                result.setResultId(200);
                result.setResultMessage("Success");
                result.setCategories(categories);
            }
            session.getAsyncRemote().sendObject(result);
        }
        catch (NoResultException | EJBException ex){
            result.setResultCode(204);
            result.setResultMessage("No results");
            session.getAsyncRemote().sendObject(result);
        }
   }

   private void getPostWithReply(GetPostReplysJSON repostEdit){
        GetResultPostWithReplyJSON success = new GetResultPostWithReplyJSON(200, "Found Results", null);
        GetResultPostWithReplyJSON failed = new GetResultPostWithReplyJSON(203, "Not Found Results", null);
        
        for(PostWithReplyJSON postWithReply : repostEdit.getPostWithReplys()){
            try{    
                postWithReply = postOps.getPostWithReplyInfo(postWithReply);
                if(postWithReply!= null){
                    Set<PostWithReplyJSON> postCats = success.getPostWithReplies();
                    if(postCats == null)
                        postCats = new HashSet<PostWithReplyJSON>();
                    postCats.add(postWithReply);
                    success.setPostWithReplies(postCats);
                }
            }
            catch(EJBException | NoResultException ex){
                if(postWithReply!= null){
                    Set<PostWithReplyJSON> postCats = failed.getPostWithReplies();
                    if(postCats == null)
                        postCats = new HashSet<PostWithReplyJSON>();
                    postCats.add(postWithReply);
                    failed.setPostWithReplies(postCats);
                }
            }
        }
        if(failed.getPostWithReplies() != null)
            session.getAsyncRemote().sendObject(failed);
        if(success.getPostWithReplies() != null)
            session.getAsyncRemote().sendObject(success);
    }
    
    private void getPost(GetPostJSON getPost){    
        GetResultPostJSON successEdits = new GetResultPostJSON(200, "Success",null);
        GetResultPostJSON failureEdits = new GetResultPostJSON(203, "Could not edit", null);

           for(PostJSON post : getPost.getPosts()){
                try{
                    PostJSON postEntity = postOps.getPostInfo(post);
                    if(postEntity!= null){
                        Set<PostJSON> postCats = successEdits.getPostResult();
                        if(postCats == null)
                            postCats = new HashSet<PostJSON>();
                        postCats.add(postEntity);
                        successEdits.setPostResult(postCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<PostJSON> postCats = failureEdits.getPostResult();
                    if(postCats == null)
                        postCats = new HashSet<PostJSON>();
                    postCats.add(post);
                    failureEdits.setPostResult(postCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<PostJSON> postCats = failureEdits.getPostResult();
                    if(postCats == null)
                        postCats = new HashSet<PostJSON>();
                    postCats.add(post);
                    failureEdits.setPostResult(postCats);
                }
        }
        if(successEdits.getPostResult() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getPostResult() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }

    private void searchPost(SearchPostJSON searchCommand){
        Set<PostJSON> posts = postOps.searchPost(searchCommand.getPost());
        //add customisation filter according to users tastes here
        SearchResultPostJSON result = new SearchResultPostJSON(null, 0, null);
        try{
            if(posts.size() == 0){
                result.setResultId(204);
                result.setResultMessage("No results");
                result.setResultId(200);
                result.setResultMessage("Success");
                result.setPosts(posts);
            }
        
            else{
                result.setResultId(200);
                result.setResultMessage("Success");
                result.setPosts(posts);
                
            }
            session.getAsyncRemote().sendObject(result);
        }
        catch(NoResultException | EJBException ex){
            result.setResultId(204);
            result.setResultMessage("No results");
            session.getAsyncRemote().sendObject(result);
        }
        
   }

   private void getTopicPost(GetTopicPostJSON getTopicPost){
        GetResultTopicPostJSON successEdits = new GetResultTopicPostJSON(200, "Success",null);
        GetResultTopicPostJSON failureEdits = new GetResultTopicPostJSON(203, "Could not edit", null);

           for(TopicPostJSON topicPost : getTopicPost.getTopicPosts()){
                try{
                    TopicPostJSON topicEntity = postOps.getTopicPostInfo(topicPost);
                    if(topicEntity!= null){
                        Set<TopicPostJSON> topicCats = successEdits.getTopicPosts();
                        topicCats.add(topicEntity);
                        successEdits.setTopicPosts(topicCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<TopicPostJSON> topicCats = failureEdits.getTopicPosts();
                    if(topicCats == null)
                        topicCats = new HashSet<TopicPostJSON>();
                    topicCats.add(topicPost);
                    failureEdits.setTopicPosts(topicCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<TopicPostJSON> topicCats = failureEdits.getTopicPosts();
                    if(topicCats == null)
                        topicCats = new HashSet<TopicPostJSON>();
                    topicCats.add(topicPost);
                    failureEdits.setTopicPosts(topicCats);
                }
        }
        if(successEdits.getTopicPosts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getTopicPosts() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }

    private void editTopicPost(EditTopicPostJSON editTopic){
        Set<TopicPostJSON> topicPosts = editTopic.getTopicPosts();
        EditResultTopicPostJSON successEdits = new EditResultTopicPostJSON(200, "Success", null);
        EditResultTopicPostJSON failureEdits = new EditResultTopicPostJSON(203, "Could not edit", null);
        for(TopicPostJSON topicPost : topicPosts){
            try{
                try{
                    postOps.editTopicPost((TopicPostJSON)topicPost);
                    TopicPostJSON topicPostEntity = postOps.getTopicPostInfo(topicPost);
                    Set<TopicPostJSON> resCats = successEdits.getTopicPosts();
                    if(resCats == null)
                        resCats = new HashSet<TopicPostJSON>();
                    resCats.add(topicPostEntity);
                    successEdits.setTopicPosts(resCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<TopicPostJSON> resCats = failureEdits.getTopicPosts();
                    if(resCats == null)
                        resCats = new HashSet<TopicPostJSON>();
                    resCats.add(topicPost);
                    failureEdits.setTopicPosts(resCats);
                }
                catch (RollbackException ex) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<TopicPostJSON> resCats = failureEdits.getTopicPosts();
                    if(resCats == null)
                        resCats = new HashSet<TopicPostJSON>();
                    resCats.add(topicPost);
                    failureEdits.setTopicPosts(resCats);
                }
            }
            catch(EJBException ex){                    
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<TopicPostJSON> resCats = failureEdits.getTopicPosts();
                if(resCats == null)
                    resCats = new HashSet<TopicPostJSON>();
                resCats.add(topicPost);
                failureEdits.setTopicPosts(resCats);
            }
                    
        }
        if(successEdits.getTopicPosts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getTopicPosts() != null)
            session.getAsyncRemote().sendObject(failureEdits);           
   }

   private void getEventPost(GetEventPostJSON getEventPost){
        GetResultEventPostJSON successEdits = new GetResultEventPostJSON(200, "Success",null);
        GetResultEventPostJSON failureEdits = new GetResultEventPostJSON(203, "Could not edit", null);

           for(EventPostJSON eventPost : getEventPost.getEventPosts()){
                try{
                    EventPostJSON eventEntity = postOps.getEventPostInfo(eventPost);
                    if(eventEntity!= null){
                        Set<EventPostJSON> eventCats = successEdits.getEventPosts();
                        eventCats.add(eventEntity);
                        successEdits.setEventPosts(eventCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<EventPostJSON> eventCats = failureEdits.getEventPosts();
                    if(eventCats == null)
                        eventCats = new HashSet<EventPostJSON>();
                    eventCats.add(eventPost);
                    failureEdits.setEventPosts(eventCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<EventPostJSON> eventCats = failureEdits.getEventPosts();
                    if(eventCats == null)
                        eventCats = new HashSet<EventPostJSON>();
                    eventCats.add(eventPost);
                    failureEdits.setEventPosts(eventCats);
                }
        }
        if(successEdits.getEventPosts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getEventPosts() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }

    private void getRepost(GetRepostJSON getRepost){
        GetResultRepostJSON successEdits = new GetResultRepostJSON(200, "Success",null);
        GetResultRepostJSON failureEdits = new GetResultRepostJSON(203, "Could not edit", null);

           for(RepostJSON repost : getRepost.getReposts()){
                try{
                    RepostJSON repostEntity = postOps.getRepostInfo(repost);
                    if(repostEntity!= null){
                        Set<RepostJSON> postCats = successEdits.getReposts();
                        postCats.add(repostEntity);
                        successEdits.setReposts(postCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<RepostJSON> postCats = failureEdits.getReposts();
                    if(postCats == null)
                        postCats = new HashSet<RepostJSON>();
                    postCats.add(repost);
                    failureEdits.setReposts(postCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<RepostJSON> postCats = failureEdits.getReposts();
                    if(postCats == null)
                        postCats = new HashSet<RepostJSON>();
                    postCats.add(repost);
                    failureEdits.setReposts(postCats);
                }
        }
        if(successEdits.getReposts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getReposts() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }
    
    private void editRepost(EditRepostJSON repostEdit){
        Set<RepostJSON> reposts = repostEdit.getPosts();
        EditResultRepostJSON successEdits = new EditResultRepostJSON(null, "success" ,200);
        EditResultRepostJSON failureEdits = new EditResultRepostJSON(null, "Could not edit" ,203);
        for(RepostJSON post : reposts){
            try{
                try{
                    postOps.editRepost(post);
                    RepostJSON postEntity = postOps.getRepostInfo(post);
                    Set<RepostJSON> resCats = successEdits.getReposts();
                    if(resCats == null)
                        resCats = new HashSet<RepostJSON>();
                    resCats.add(postEntity);
                    successEdits.setReposts(resCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<RepostJSON> resCats = failureEdits.getReposts();
                    if(resCats == null)
                        resCats = new HashSet<RepostJSON>();
                    resCats.add(post);
                    failureEdits.setReposts(resCats);
                }
                catch (RollbackException ex) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<RepostJSON> resCats = failureEdits.getReposts();
                    if(resCats == null)
                        resCats = new HashSet<RepostJSON>();
                    resCats.add(post);
                    failureEdits.setReposts(resCats);
                }
            }
            catch(EJBException ex){                    
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<RepostJSON> resCats = failureEdits.getReposts();
                if(resCats == null)
                    resCats = new HashSet<RepostJSON>();
                resCats.add(post);
                failureEdits.setReposts(resCats);
            }
                    
        }
        if(successEdits.getReposts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getReposts() != null)
            session.getAsyncRemote().sendObject(failureEdits);                   
   }

   private void editEventNotification(EditEventNotificationJSON not){       
        Set<EventNotificationJSON> eventNots = not.getEventNotifications();
        EditResultEventNotificationJSON successEdits = new EditResultEventNotificationJSON(200,"Success", null);
        EditResultEventNotificationJSON failureEdits = new EditResultEventNotificationJSON(200,"Could not edit", null);

        for(EventNotificationJSON event : eventNots){
            try{
               if(event.isCanceled()){
                    try{
                        notOps.editEventNotification(event);
                        Set<EventNotificationJSON> eventCats  = successEdits.getEventNotifications();
                        if(eventCats == null)
                            eventCats = new HashSet<EventNotificationJSON>();
                        eventCats.add(event);
                        successEdits.setEventNotifications(eventNots);
                    }
                    catch (NoResultException e) {
                        Set<EventNotificationJSON> eventCats  = successEdits.getEventNotifications();
                        if(eventCats == null)
                        eventCats = new HashSet<EventNotificationJSON>();
                        eventCats.add(event);
                        successEdits.setEventNotifications(eventNots);
                    }
                }
                else{
                    try{
                        Set<EventNotificationJSON> candidates = notOps.getEventNotificationInfo(event);
                        if(candidates == null || candidates.size() > 1)
                            throw new NoResultException();
                    }
                    catch(NoResultException ex){
                        failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                        Set<EventNotificationJSON> eventCats  = failureEdits.getEventNotifications();
                        if(eventCats == null)
                            eventCats = new HashSet<EventNotificationJSON>();
                        eventCats.add(event);
                        failureEdits.setEventNotifications(eventNots);
                    }         
                }
            }
            catch (RollbackException ex) {
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<EventNotificationJSON> eventCats  = failureEdits.getEventNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<EventNotificationJSON>();
                eventCats.add(event);
                failureEdits.setEventNotifications(eventNots);
            }
            catch(EJBException ex){
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<EventNotificationJSON> eventCats  = failureEdits.getEventNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<EventNotificationJSON>();
                eventCats.add(event);
                failureEdits.setEventNotifications(eventNots);
            }
                    
        }
        if(successEdits.getEventNotifications() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getEventNotifications() != null)
            session.getAsyncRemote().sendObject(failureEdits);       
   }

   private void getEventNotification(GetEventNotificationJSON getEvent){
        for(EventNotificationJSON event : getEvent.getEventNotifications()){
            try{
                Set<EventNotificationJSON> events = notOps.getEventNotificationInfo(event);
                if(events != null){
                    GetResultEventNotificationJSON found = new GetResultEventNotificationJSON(200, "Found events", null);
                    found.setEventNotifications(events);
                    session.getAsyncRemote().sendObject(found);
                }
                else{
                    GetResultEventNotificationJSON notFound = new GetResultEventNotificationJSON( 203, "Not found Events", null);
                    session.getAsyncRemote().sendObject(notFound);
                }
            }
            catch(EJBException ex){
                GetResultEventNotificationJSON notFound = new GetResultEventNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
            catch(NoResultException ex){
                GetResultEventNotificationJSON notFound = new GetResultEventNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }
    }

    private void editEventPostNotification(EditEventPostNotificationJSON postNotEdit){
        Set<EventPostNotificationJSON> eventNots = postNotEdit.getEventPostNotifications();
        EditResultEventPostNotificationJSON successEdits = new EditResultEventPostNotificationJSON(200,"Success", null);
        EditResultEventPostNotificationJSON failureEdits = new EditResultEventPostNotificationJSON(200,"Could not edit", null);

        for(EventPostNotificationJSON event : eventNots){
            try{
                notOps.editEventPostNotification(event);
                Set<EventPostNotificationJSON> candidates = notOps.getEventPostNotificationInfo(event);
                if(candidates == null || candidates.size() > 1)
                    throw new NoResultException();
                else{
                    Set<EventPostNotificationJSON> eventCats  = failureEdits.getEventPostNotifications();
                    if(eventCats == null)
                        eventCats = new HashSet<EventPostNotificationJSON>();
                        eventCats.addAll(candidates);
                        failureEdits.setEventPostNotifications(eventNots);
                }
            }
            catch(NoResultException | EJBException  ex){
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<EventPostNotificationJSON> eventCats  = failureEdits.getEventPostNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<EventPostNotificationJSON>();
                    eventCats.add(event);
                    failureEdits.setEventPostNotifications(eventNots);
            }
        }         
        if(successEdits.getEventPostNotifications() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getEventPostNotifications() != null)
            session.getAsyncRemote().sendObject(failureEdits);        
   }

   private void getEventPostNotification(GetEventPostNotificationJSON getEvent){
        for(EventPostNotificationJSON event : getEvent.getEventPostNotifications()){
            try{
                Set<EventPostNotificationJSON> events = notOps.getEventPostNotificationInfo(event);
                if(events != null){
                    GetResultEventPostNotificationJSON found = new GetResultEventPostNotificationJSON(200, "Found events", null);
                    found.setEventPostNotifications(events);
                    session.getAsyncRemote().sendObject(found);
                }
                else{
                    GetResultEventPostNotificationJSON notFound = new GetResultEventPostNotificationJSON( 203, "Not found Events", null);
                    session.getAsyncRemote().sendObject(notFound);
                }
            }
            catch(EJBException ex){
                GetResultEventPostNotificationJSON notFound = new GetResultEventPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
            catch(NoResultException ex){
                GetResultEventPostNotificationJSON notFound = new GetResultEventPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }
    }

private void editTopicPostNotification(EditTopicPostNotificationJSON postEdit){
        Set<TopicPostNotificationJSON> eventNots = postEdit.getTopicPostNotifications();
        EditResultTopicPostNotificationJSON successEdits = new EditResultTopicPostNotificationJSON(200,"Success", null);
        EditResultTopicPostNotificationJSON failureEdits = new EditResultTopicPostNotificationJSON(200,"Could not edit", null);

        for(TopicPostNotificationJSON topic : eventNots){
            try{
                notOps.editTopicPostNotification(topic);
                Set<TopicPostNotificationJSON> topicPostNot = notOps.getTopicPostNotificationInfo(topic);
                if(topicPostNot.size() > 1)
                    throw new NoResultException("Multiple results");
                Set<TopicPostNotificationJSON> eventCats  = successEdits.getTopicsPostNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<TopicPostNotificationJSON>();
                eventCats.addAll(topicPostNot);
                successEdits.setTopicPostNotifications(eventNots);
            }
            catch (NoResultException e) {
                if(topic.isCanceled()){
                    Set<TopicPostNotificationJSON> eventCats  = successEdits.getTopicsPostNotifications();
                    eventCats = new HashSet<TopicPostNotificationJSON>();
                    eventCats.add(topic);
                    successEdits.setTopicPostNotifications(eventNots);
                }
                else{
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<TopicPostNotificationJSON> eventCats  = failureEdits.getTopicsPostNotifications();
                    if(eventCats == null)
                        eventCats = new HashSet<TopicPostNotificationJSON>();
                    eventCats.add(topic);
                failureEdits.setTopicPostNotifications(eventNots);    
                }
            }
            catch (RollbackException ex) {
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<TopicPostNotificationJSON> eventCats  = failureEdits.getTopicsPostNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<TopicPostNotificationJSON>();
                eventCats.add(topic);
                failureEdits.setTopicPostNotifications(eventNots);
            }
            catch(EJBException ex){
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<TopicPostNotificationJSON> eventCats  = failureEdits.getTopicsPostNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<TopicPostNotificationJSON>();
                eventCats.add(topic);
                failureEdits.setTopicPostNotifications(eventNots);
            }
                    
        }
        if(successEdits.getTopicsPostNotifications() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getTopicsPostNotifications() != null)
            session.getAsyncRemote().sendObject(failureEdits);        
   }

    private void getTopicPostNotification(GetTopicPostNotificationJSON getEvent){        
        for(TopicPostNotificationJSON topic : getEvent.getTopicPostNotifications()){
            try{
                Set<TopicPostNotificationJSON> topics = notOps.getTopicPostNotificationInfo(topic);
                if(topics != null){
                    GetResultTopicPostNotificationJSON found = new GetResultTopicPostNotificationJSON(200, "Found events", null);
                    found.setTopicPostNotifications(topics);
                    session.getAsyncRemote().sendObject(found);
                }
                else{
                    GetResultTopicPostNotificationJSON notFound = new GetResultTopicPostNotificationJSON( 203, "Not found Events", null);
                    session.getAsyncRemote().sendObject(notFound);
                }
            }
            catch(EJBException ex){
                GetResultTopicPostNotificationJSON notFound = new GetResultTopicPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
            catch(NoResultException ex){
                GetResultTopicPostNotificationJSON notFound = new GetResultTopicPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }
    }

    private void editPostNotification(EditPostNotificationJSON eventEdit){
        Set<PostNotificationJSON> eventNots = eventEdit.getPostNotifications();
        EditResultPostNotificationJSON successEdits = new EditResultPostNotificationJSON(null, 200, "Success");
        EditResultPostNotificationJSON failureEdits = new EditResultPostNotificationJSON(null, 203, "Could not edit");

        for(PostNotificationJSON event : eventNots){ 
            try{
                notOps.editPostNotification(event);
                Set<PostNotificationJSON> candidates = notOps.getPostNotificationInfo(event);
                if(candidates == null || candidates.size() > 1)
                    throw new NoResultException();
                else{
                    Set<PostNotificationJSON> eventCats  = failureEdits.getPostNotifications();
                    if(eventCats == null)
                        eventCats = new HashSet<PostNotificationJSON>();
                        eventCats.addAll(candidates);
                        failureEdits.setPostNotifications(eventNots);
                }
            }
            catch(NoResultException | EJBException  ex){
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<PostNotificationJSON> eventCats  = failureEdits.getPostNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<PostNotificationJSON>();
                    eventCats.add(event);
                    failureEdits.setPostNotifications(eventNots);
            }
                    
        }
        if(successEdits.getPostNotifications() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getPostNotifications() != null)
            session.getAsyncRemote().sendObject(failureEdits);          
   } 

   private void getPostNotification(GetPostNotificationJSON getEvent){
        for(PostNotificationJSON event : getEvent.getPostNotifications()){
            try{
                Set<PostNotificationJSON> events = notOps.getPostNotificationInfo(event);
                if(events != null){
                    GetResultPostNotificationJSON found = new GetResultPostNotificationJSON(200, "Found events", null);
                    found.setPostNotifications(events);
                    session.getAsyncRemote().sendObject(found);
                }
                else{
                    GetResultPostNotificationJSON notFound = new GetResultPostNotificationJSON( 203, "Not found Events", null);
                    session.getAsyncRemote().sendObject(notFound);
                }
            }
            catch(EJBException ex){
                GetResultPostNotificationJSON notFound = new GetResultPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
            catch(NoResultException ex){
                GetResultPostNotificationJSON notFound = new GetResultPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }    
    }

    private void editTopicFollowNotification(EditTopicFollowNotificationJSON eventEdit){
        Set<TopicFollowNotificationJSON> followNots = eventEdit.getTopicFollowNotifications();
        EditResultTopicFollowNotificationJSON successEdits = new EditResultTopicFollowNotificationJSON( 200, "Success", null);
        EditResultTopicFollowNotificationJSON failureEdits = new EditResultTopicFollowNotificationJSON( 203, "Could not edit", null);

        for(TopicFollowNotificationJSON topicFollow : followNots){
            try{
                notOps.editTopicFollowNotification(topicFollow);
                Set<TopicFollowNotificationJSON> topicFollowEntities = notOps.getTopicFollowNotificationInfo(topicFollow);
                if(topicFollowEntities.size() > 1)
                    throw new NoResultException("Multiple results");
                Set<TopicFollowNotificationJSON> eventCats  = successEdits.getTopicFollowNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<TopicFollowNotificationJSON>();
                eventCats.addAll(topicFollowEntities);
                successEdits.setTopicFollowNotifications(eventCats);
            }
            catch (NoResultException e) {
                if(topicFollow.isCanceled()){
                        Set<TopicFollowNotificationJSON> eventCats  = successEdits.getTopicFollowNotifications();
                        if(eventCats == null)
                            eventCats = new HashSet<TopicFollowNotificationJSON>();
                        eventCats.add(topicFollow);
                        successEdits.setTopicFollowNotifications(eventCats);
                }
                else{
                    successEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<TopicFollowNotificationJSON> eventCats  = failureEdits.getTopicFollowNotifications();
                    if(eventCats == null)
                        eventCats = new HashSet<TopicFollowNotificationJSON>();
                        eventCats.add(topicFollow);
                    successEdits.setTopicFollowNotifications(eventCats);
                }
            }
            catch (RollbackException ex) {
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<TopicFollowNotificationJSON> eventCats  = failureEdits.getTopicFollowNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<TopicFollowNotificationJSON>();
                eventCats.add(topicFollow);
                failureEdits.setTopicFollowNotifications(eventCats);
            }
            catch(EJBException ex){
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<TopicFollowNotificationJSON> eventCats  = failureEdits.getTopicFollowNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<TopicFollowNotificationJSON>();
                eventCats.add(topicFollow);
                failureEdits.setTopicFollowNotifications(eventCats);
            }
                    
        }
        if(successEdits.getTopicFollowNotifications() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getTopicFollowNotifications() != null)
            session.getAsyncRemote().sendObject(failureEdits);        
   }

   private void getTopicFollowNotification(GetTopicFollowNotificationJSON getEvent){
        for(TopicFollowNotificationJSON event : getEvent.getTopicFollowNotifications()){
            try{
                Set<TopicFollowNotificationJSON> events = notOps.getTopicFollowNotificationInfo(event);
                if(events != null){
                    GetResultTopicFollowNotificationJSON found = new GetResultTopicFollowNotificationJSON(200, "Found events", null);
                    found.setTopicFollowNotifications(events);
                    session.getAsyncRemote().sendObject(found);
                }
                else{
                    GetResultTopicFollowNotificationJSON notFound = new GetResultTopicFollowNotificationJSON( 203, "Not found Events", null);
                    session.getAsyncRemote().sendObject(notFound);
                }
            }
            catch(EJBException ex){
                GetResultTopicFollowNotificationJSON notFound = new GetResultTopicFollowNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
            catch(NoResultException ex){
                GetResultTopicFollowNotificationJSON notFound = new GetResultTopicFollowNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }
    }

    private void getUserFollowNotification(GetUserFollowNotificationJSON getEvent){
        for(UserFollowNotificationJSON event : getEvent.getUserFollowNotifications()){
            try{
                Set<UserFollowNotificationJSON> events = notOps.getUserFollowNotificationInfo(event);
                if(events != null){
                    GetResultUserFollowNotificationJSON found = new GetResultUserFollowNotificationJSON(200, "Found events", null);
                    found.setUserFollowNotifications(events);
                    session.getAsyncRemote().sendObject(found);
                }
                else{
                    GetResultUserFollowNotificationJSON notFound = new GetResultUserFollowNotificationJSON( 203, "Not found Events", null);
                    session.getAsyncRemote().sendObject(notFound);
                }
            }
            catch(EJBException ex){
                GetResultUserFollowNotificationJSON notFound = new GetResultUserFollowNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
            catch(NoResultException ex){
                GetResultUserFollowNotificationJSON notFound = new GetResultUserFollowNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }
        
    }

    private void editUserFollowNotification(EditUserFollowNotificationJSON postEdit){
        Set<UserFollowNotificationJSON> followNots = postEdit.getUserFollowNotifications();
        EditResultUserFollowNotificationJSON successEdits = new EditResultUserFollowNotificationJSON( 200, "Success", null);
        EditResultUserFollowNotificationJSON failureEdits = new EditResultUserFollowNotificationJSON( 203, "Could not edit", null);

        for(UserFollowNotificationJSON userFollow : followNots){
            try{
                notOps.editUserFollowNotification(userFollow);
                Set<UserFollowNotificationJSON> topicFollowEntities = notOps.getUserFollowNotificationInfo(userFollow);
                if(topicFollowEntities.size() > 1)
                    throw new NoResultException("Multiple results");
                Set<UserFollowNotificationJSON> eventCats  = successEdits.getUserFollowNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<UserFollowNotificationJSON>();
                eventCats.addAll(topicFollowEntities);
                successEdits.setUserFollowNotifications(eventCats);
            }
            catch (NoResultException e) {
                if(userFollow.isCanceled()){
                        Set<UserFollowNotificationJSON> eventCats  = successEdits.getUserFollowNotifications();
                        if(eventCats == null)
                            eventCats = new HashSet<UserFollowNotificationJSON>();
                        eventCats.add(userFollow);
                        successEdits.setUserFollowNotifications(eventCats);
                }
                else{
                    successEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<UserFollowNotificationJSON> eventCats  = failureEdits.getUserFollowNotifications();
                    if(eventCats == null)
                        eventCats = new HashSet<UserFollowNotificationJSON>();
                        eventCats.add(userFollow);
                    successEdits.setUserFollowNotifications(eventCats);
                }
            }
            catch (RollbackException ex) {
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<UserFollowNotificationJSON> eventCats  = failureEdits.getUserFollowNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<UserFollowNotificationJSON>();
                eventCats.add(userFollow);
                failureEdits.setUserFollowNotifications(eventCats);
            }
            catch(EJBException ex){
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<UserFollowNotificationJSON> eventCats  = failureEdits.getUserFollowNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<UserFollowNotificationJSON>();
                eventCats.add(userFollow);
                failureEdits.setUserFollowNotifications(eventCats);
            }
                    
        }
        if(successEdits.getUserFollowNotifications() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getUserFollowNotifications() != null)
            session.getAsyncRemote().sendObject(failureEdits);                
   }

}