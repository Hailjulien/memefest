package com.memefest.Websockets.MessageHandlers;

import java.time.LocalDateTime;
import java.util.Map;

import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Websockets.JSON.EditRepostJSON;
import com.memefest.Websockets.JSON.EditScheduledEventJSON;
import com.memefest.Websockets.JSON.EditScheduledTopicJSON;
import com.memefest.Websockets.JSON.GetPostReplysJSON;
import com.memefest.Websockets.JSON.GetResultScheduledEventJSON;
import com.memefest.Websockets.JSON.GetScheduledEventJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetScheduledEventMessageHandler implements MessageHandler.Whole<GetScheduledEventJSON>{
    
    private EventOperations eventOps;
    private Session session; 

    public GetScheduledEventMessageHandler(EventOperations eventOps, Session session){
        this.eventOps = eventOps;
        this.session = session;
    }   

    @Override
    public  void onMessage(GetScheduledEventJSON repostEdit){
        for(EventJSON event : repostEdit.getEvents()){
            try{
                Map<EventJSON, LocalDateTime> eventTimes = eventOps.getScheduledEvents(event);
                GetResultScheduledEventJSON success = new GetResultScheduledEventJSON(200, "Successful Results", eventTimes);
                session.getAsyncRemote().sendObject(success);
            }
            catch(EJBException ex){
                GetResultScheduledEventJSON  failed = new GetResultScheduledEventJSON(203, ex.getMessage(), null);
                session.getAsyncRemote().sendObject(failed);
            }
        }  
    }
}
