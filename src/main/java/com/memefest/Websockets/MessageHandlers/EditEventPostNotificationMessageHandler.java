package com.memefest.Websockets.MessageHandlers;


import java.util.HashSet;
import java.util.Set;

import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.EditEventPostNotificationJSON;
import com.memefest.Websockets.JSON.EditResultEventPostNotificationJSON;
import com.memefest.Websockets.JSON.EventPostNotificationJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;

public class EditEventPostNotificationMessageHandler implements MessageHandler.Whole<EditEventPostNotificationJSON>{

   private NotificationOperations notOps;
   private Session session;

   public EditEventPostNotificationMessageHandler(NotificationOperations notOps, Session session){
       this.notOps = notOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
    @Override
   public  void onMessage(EditEventPostNotificationJSON postNotEdit){
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
   
}
