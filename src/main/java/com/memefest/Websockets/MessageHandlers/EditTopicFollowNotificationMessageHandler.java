package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;
import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.EditResultTopicFollowNotificationJSON;
import com.memefest.Websockets.JSON.EditTopicFollowNotificationJSON;
import com.memefest.Websockets.JSON.TopicFollowNotificationJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;

public class EditTopicFollowNotificationMessageHandler implements MessageHandler.Whole<EditTopicFollowNotificationJSON>{

   private NotificationOperations notOps;
   private Session session;

   public EditTopicFollowNotificationMessageHandler(NotificationOperations notOps, Session session){
       this.notOps = notOps;
       this.session = session;
   }
   
   //add customisation filter according to users tastes here
    @Override
   public  void onMessage(EditTopicFollowNotificationJSON eventEdit){
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
}