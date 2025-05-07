package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.EditEventNotificationJSON;
import com.memefest.Websockets.JSON.EditResultEventNotificationJSON;
import com.memefest.Websockets.JSON.EventNotificationJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;

public class EditEventNotificationMessageHandler implements MessageHandler.Whole<EditEventNotificationJSON>{

   private NotificationOperations notOps;
   private Session session;

   public EditEventNotificationMessageHandler(NotificationOperations notOps, Session session){
       this.notOps = notOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
      @Override
   public  void onMessage(EditEventNotificationJSON not){       
        Set<EventNotificationJSON> eventNots = not.getEventNotifications();
        EditResultEventNotificationJSON successEdits = new EditResultEventNotificationJSON(200,"Success", null);
        EditResultEventNotificationJSON failureEdits = new EditResultEventNotificationJSON(200,"Could not edit", null);

        for(EventNotificationJSON event : eventNots){
            try{
               if(event.isCanceled()){
                    try{
                        notOps.removeEventNotification(event);
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
  
}