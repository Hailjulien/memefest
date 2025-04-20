package com.memefest.Websockets.MessageHandlers;
import java.util.Set;

import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Websockets.JSON.EditEventJSON;
import com.memefest.Websockets.JSON.EditResultEventJSON;

import jakarta.websocket.Session;
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
            eventOps.editEvent(event);
            EventJSON eventEntity = eventOps.getEventInfo(event);
            if(eventEntity!= null){
                Set<EventJSON> resEvents = successEdits.getEvents();
                resEvents.add(eventEntity);
                successEdits.setEvents(resEvents);
            }
            else{
                Set<EventJSON> resReposts = failureEdits.getEvents();
                resReposts.add(event);
                failureEdits.setEvents(resReposts);
            }
        }
        session.getAsyncRemote().sendObject(successEdits);
        session.getAsyncRemote().sendObject(failureEdits);
        //send updated post to user who initiated the edit operation        
   }
}
