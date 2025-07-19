package com.memefest.Websockets;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.Services.CategoryOperations;
import com.memefest.Services.EventOperations;
import com.memefest.Services.FeedsOperations;
import com.memefest.Services.NotificationOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Services.UserOperations;
import com.memefest.Websockets.JSON.EditEventJSON;
import com.memefest.Websockets.JSON.EditResultEventJSON;
import com.memefest.Websockets.MessageHandlers.EditUserMessageHandler;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ejb.PostActivate;
import jakarta.ejb.Stateful;
import jakarta.persistence.NoResultException;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;

@Stateful(name = "feedsEndpoint")
public class FeedsEndpoint extends Endpoint{
    
    
    @EJB
    private FeedsOperations feedsServer;

    @EJB
    private PostOperations postOperations;

    @EJB
    private UserOperations userOperations;

    @EJB 
    private CategoryOperations catOps;

    @EJB
    private EventOperations eventOps;

    @EJB
    private NotificationOperations notOps;

    @EJB
    private TopicOperations topicOps;

    @PostConstruct
    @PostActivate
    public void init() {
        
    }

    @Override
    public void onOpen(Session session, EndpointConfig config){
        //notifiacation about user joining room
        //timer to monitor time spent for each student in session
        
        feedsServer.addClient(session);
        session.addMessageHandler(new EditUserMessageHandler(userOperations,topicOps,catOps,postOperations,eventOps,notOps,session));
        /*
        Set<EventNotificationJSON> eventNot = notOps.getEventNotificationInfo(new EventNotificationJSON(0, null, null, new UserJSON(session.getUserPrincipal().getName())));
        Set<EventPostNotificationJSON> eventPostNots = notOps.getEventPostNotificationInfo(new EventPostNotificationJSON(0, null, null, new UserJSON(session.getUserPrincipal().getName())));
        Set<PostNotificationJSON> postNots = notOps.getPostNotificationInfo(new PostNotificationJSON(0, null, null, new UserJSON(session.getUserPrincipal().getName())));
        notOps.getTopicFollowNotificationInfo(new TopicFollowNotificationJSON(0, null, null, null));
        */
        
    }

    @OnMessage
    public void onMessage(Session session, EditEventJSON eventEdit){
            Set<EventJSON> events = eventEdit.getEvents();
            EditResultEventJSON successEdits = new EditResultEventJSON(null, 200, "Success");
            EditResultEventJSON failureEdits = new EditResultEventJSON(null, 203, "Could not edit");

            for(EventJSON event : events){
                try{
                    eventOps.editEvent((EventJSON)event);
                EventJSON eventEntity = eventOps.getEventInfo((EventJSON)event);
                Set<EventJSON> resEvent = successEdits.getEvents();
                if(resEvent == null)
                    resEvent = new HashSet<EventJSON>();
                resEvent.add(eventEntity);
                successEdits.setEvents(resEvent);
            } 
            catch (NoResultException | EJBException e) {
                Set<EventJSON> resEvent = failureEdits.getEvents();
                if(resEvent == null)
                    resEvent = new HashSet<EventJSON>();
                resEvent.add(event);
                failureEdits.setEvents(resEvent);
            }
        }
        Set<EditResultEventJSON> editResults = new HashSet<EditResultEventJSON>();
        if(successEdits.getEvents() != null)
            editResults.add(successEdits);
        if(failureEdits.getEvents() != null)
            editResults.add(failureEdits);
        session.getAsyncRemote().sendObject(editResults);           
   }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
     
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
           feedsServer.removeClient(session);
    }

    @Override
    public void onError(Session session, Throwable throwable){
        try{
            session.getBasicRemote().sendText("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOps");
            session.getBasicRemote().sendText(throwable.getMessage());
            session.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        feedsServer.removeClient(session);
    }

    //create a notification message to show logged in user
 
}
