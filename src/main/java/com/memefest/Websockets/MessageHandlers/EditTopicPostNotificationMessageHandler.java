package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.EditResultTopicPostNotificationJSON;
import com.memefest.Websockets.JSON.EditTopicPostNotificationJSON;
import com.memefest.Websockets.JSON.TopicPostNotificationJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;

public class EditTopicPostNotificationMessageHandler implements MessageHandler.Whole<EditTopicPostNotificationJSON>{

   private NotificationOperations notOps;
   private Session session;

   public EditTopicPostNotificationMessageHandler(NotificationOperations notOps, Session session){
       this.notOps = notOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
    @Override
   public  void onMessage(EditTopicPostNotificationJSON postEdit){
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
   
}
