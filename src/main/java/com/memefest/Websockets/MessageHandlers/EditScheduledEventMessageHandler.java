package com.memefest.Websockets.MessageHandlers;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Websockets.JSON.EditRepostJSON;
import com.memefest.Websockets.JSON.EditResultScheduledEventJSON;
import com.memefest.Websockets.JSON.EditScheduledEventJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class EditScheduledEventMessageHandler implements MessageHandler.Whole<EditScheduledEventJSON>{
    

    private EventOperations eventOps;
    private Session session; 

    public EditScheduledEventMessageHandler(EventOperations eventOps, Session session){
        this.eventOps = eventOps;
        this.session = session;
    }   

    @Override
    public  void onMessage(EditScheduledEventJSON repostEdit){
        EditResultScheduledEventJSON successful = new EditResultScheduledEventJSON(200, "Found Results", null);
        EditResultScheduledEventJSON failed = new EditResultScheduledEventJSON(203, "", null);
        Map<EventJSON, LocalDateTime> eventTimes = repostEdit.getEventTimes();
        try{
            eventOps.editScheduledEvent(eventTimes);
            successful.setEventTimes(eventTimes);
        }
        catch(EJBException ex){
            failed.setEventTimes(eventTimes);    
        }
                if(successful.getEventTimes() != null){
            session.getAsyncRemote().sendObject(successful);
        }
        if(failed.getEventTimes() != null){
            session.getAsyncRemote().sendObject(failed);
        }
   }
}
