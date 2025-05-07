package com.memefest.Websockets.MessageHandlers;
import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Websockets.JSON.EditEventJSON;
import com.memefest.Websockets.JSON.EditResultEventJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;

public class EditEventMessageHandler implements MessageHandler.Whole<EditEventJSON>{

   private EventOperations eventOps;
   private Session session;

   public EditEventMessageHandler(EventOperations eventOps, Session session){
       this.eventOps = eventOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
      @Override
   public  void onMessage(EditEventJSON eventEdit){ 
        Set<EventJSON> events = eventEdit.getEvents();
        
        EditResultEventJSON successEdits = new EditResultEventJSON(null, 200, "Success");
        EditResultEventJSON failureEdits = new EditResultEventJSON(null, 203, "Could not edit");

        for(EventJSON event : events){
            try{
                eventOps.editEvent((EventJSON)event);
                try{
                    EventJSON eventEntity = eventOps.getEventInfo((EventJSON)event);
                    Set<EventJSON> resEvent = successEdits.getEvents();
                    if(resEvent == null)
                        resEvent = new HashSet<EventJSON>();
                    resEvent.add(eventEntity);
                    successEdits.setEvents(resEvent);
                } 
                catch (NoResultException e) {
                        Set<EventJSON> resEvent = failureEdits.getEvents();
                        if(resEvent == null)
                            resEvent = new HashSet<EventJSON>();
                        resEvent.add(event);
                        failureEdits.setEvents(resEvent);
                }
                catch (RollbackException ex) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<EventJSON> resEvents = failureEdits.getEvents();
                    if(resEvents == null)
                        resEvents = new HashSet<EventJSON>();
                    resEvents.add(event);
                    failureEdits.setEvents(resEvents);
                }
            }
            catch(EJBException ex){
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<EventJSON> resEvents = failureEdits.getEvents();
                if(resEvents == null)
                    resEvents= new HashSet<EventJSON>();
                resEvents.add(event);
                failureEdits.setEvents(resEvents);
            }
                    
        }
        if(successEdits.getEvents() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getEvents() != null)
            session.getAsyncRemote().sendObject(failureEdits);
               
   }
          
}
