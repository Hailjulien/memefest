package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Websockets.JSON.GetEventJSON;
import com.memefest.Websockets.JSON.GetResultEventJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
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
        GetResultEventJSON successEdits = new GetResultEventJSON(200, "Success",null);
        GetResultEventJSON failureEdits = new GetResultEventJSON(203, "Could not edit", null);

           for(EventJSON event : getEvent.getEvents()){
                try{
                    EventJSON eventEntity = eventOps.getEventInfo(event);
                    if(eventEntity!= null){
                        Set<EventJSON> eventCats = successEdits.getEventResults();
                        eventCats.add(eventEntity);
                        successEdits.setEventResults(eventCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<EventJSON> eventCats = failureEdits.getEventResults();
                    if(eventCats == null)
                        eventCats = new HashSet<EventJSON>();
                    eventCats.add(event);
                    failureEdits.setEventResults(eventCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<EventJSON> eventCats = failureEdits.getEventResults();
                    if(eventCats == null)
                        eventCats = new HashSet<EventJSON>();
                    eventCats.add(event);
                    failureEdits.setEventResults(eventCats);
                }
        }
        if(successEdits.getEventResults() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getEventResults() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }
}
