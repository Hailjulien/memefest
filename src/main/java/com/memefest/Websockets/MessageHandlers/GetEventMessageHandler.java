package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Websockets.JSON.GetEventJSON;
import com.memefest.Websockets.JSON.GetEventResultJSON;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetEventMessageHandler implements MessageHandler.Whole<GetEventJSON>{

    private EventOperations eventOps;
    private Session session;
 
    public GetEventMessageHandler(EventOperations eventOps, Session session){
        this.eventOps = eventOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetEventJSON getEvent){
        GetEventResultJSON notFound = new GetEventResultJSON( 203, "Not found Events", null);
        GetEventResultJSON found = new GetEventResultJSON(200, "Found events", null);
        for(EventJSON event : getEvent.getEvents()){
            event = eventOps.getEventInfo(event);
            if(event != null){
                Set<EventJSON> events = found.getEventResults();
                events.add(event);
                found.setEventResults(events);
            }
            else{
                Set<EventJSON> events = notFound.getEventResults();
                events.add(event);
                notFound.setEventResults(events);
            }
        }
        session.getAsyncRemote().sendObject(found);
        session.getAsyncRemote().sendObject(notFound);
    }
}
