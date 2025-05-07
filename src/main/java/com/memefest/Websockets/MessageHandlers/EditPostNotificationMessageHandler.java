package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.EditPostNotificationJSON;
import com.memefest.Websockets.JSON.EditResultPostNotificationJSON;
import com.memefest.Websockets.JSON.PostNotificationJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;

public class EditPostNotificationMessageHandler implements MessageHandler.Whole<EditPostNotificationJSON>{

   private NotificationOperations notOps;
   private Session session;

   public EditPostNotificationMessageHandler(NotificationOperations notOps, Session session){
       this.notOps = notOps;
       this.session = session;
   }
   //add customisation filter according to users tastes here
      @Override
   public  void onMessage(EditPostNotificationJSON eventEdit){
        Set<PostNotificationJSON> eventNots = eventEdit.getPostNotifications();
        EditResultPostNotificationJSON successEdits = new EditResultPostNotificationJSON(null, 200, "Success");
        EditResultPostNotificationJSON failureEdits = new EditResultPostNotificationJSON(null, 203, "Could not edit");

        for(PostNotificationJSON event : eventNots){
            try{
               if(event.isCanceled()){
                    try{
                        notOps.removePostNotification(event);
                        Set<PostNotificationJSON> eventCats  = successEdits.getPostNotifications();
                        if(eventCats == null)
                            eventCats = new HashSet<PostNotificationJSON>();
                        eventCats.add(event);
                        successEdits.setPostNotifications(eventNots);
                    }
                    catch (NoResultException e) {
                        Set<PostNotificationJSON> eventCats  = successEdits.getPostNotifications();
                        if(eventCats == null)
                        eventCats = new HashSet<PostNotificationJSON>();
                        eventCats.add(event);
                        successEdits.setPostNotifications(eventNots);
                    }
                }
                else{
                    try{
                        Set<PostNotificationJSON> candidates = notOps.getPostNotificationInfo(event);
                        if(candidates == null || candidates.size() > 1)
                            throw new NoResultException();
                    }
                    catch(NoResultException ex){
                        failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                        Set<PostNotificationJSON> eventCats  = failureEdits.getPostNotifications();
                        if(eventCats == null)
                            eventCats = new HashSet<PostNotificationJSON>();
                        eventCats.add(event);
                        failureEdits.setPostNotifications(eventNots);
                    }         
                }
            }
            catch (RollbackException ex) {
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<PostNotificationJSON> eventCats  = failureEdits.getPostNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<PostNotificationJSON>();
                eventCats.add(event);
                failureEdits.setPostNotifications(eventNots);
            }
            catch(EJBException ex){
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
}
