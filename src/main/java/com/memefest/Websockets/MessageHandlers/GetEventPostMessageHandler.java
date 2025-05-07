package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.GetEventPostJSON;
import com.memefest.Websockets.JSON.GetResultEventPostJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetEventPostMessageHandler implements MessageHandler.Whole<GetEventPostJSON>{

    private PostOperations postOps;
    private Session session;
 
    public GetEventPostMessageHandler(PostOperations postOps, Session session){
        this.postOps = postOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetEventPostJSON getEventPost){
        GetResultEventPostJSON successEdits = new GetResultEventPostJSON(200, "Success",null);
        GetResultEventPostJSON failureEdits = new GetResultEventPostJSON(203, "Could not edit", null);

           for(EventPostJSON eventPost : getEventPost.getEventPosts()){
                try{
                    EventPostJSON eventEntity = postOps.getEventPostInfo(eventPost);
                    if(eventEntity!= null){
                        Set<EventPostJSON> eventCats = successEdits.getEventPosts();
                        eventCats.add(eventEntity);
                        successEdits.setEventPosts(eventCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<EventPostJSON> eventCats = failureEdits.getEventPosts();
                    if(eventCats == null)
                        eventCats = new HashSet<EventPostJSON>();
                    eventCats.add(eventPost);
                    failureEdits.setEventPosts(eventCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<EventPostJSON> eventCats = failureEdits.getEventPosts();
                    if(eventCats == null)
                        eventCats = new HashSet<EventPostJSON>();
                    eventCats.add(eventPost);
                    failureEdits.setEventPosts(eventCats);
                }
        }
        if(successEdits.getEventPosts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getEventPosts() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }
}
