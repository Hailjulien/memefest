package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.EventNotificationJSON;
import com.memefest.Websockets.JSON.GetEventNotificationJSON;
import com.memefest.Websockets.JSON.GetResultEventNotificationJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetEventNotificationMessageHandler implements MessageHandler.Whole<GetEventNotificationJSON>{

    private NotificationOperations notOps;
    private Session session;
 
    public GetEventNotificationMessageHandler(NotificationOperations notOps, Session session){
        this.notOps = notOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetEventNotificationJSON getEvent){
        for(EventNotificationJSON event : getEvent.getEventNotifications()){
            try{
                Set<EventNotificationJSON> events = notOps.getEventNotificationInfo(event);
                if(events != null){
                    GetResultEventNotificationJSON found = new GetResultEventNotificationJSON(200, "Found events", null);
                    found.setEventNotifications(events);
                    session.getAsyncRemote().sendObject(found);
                }
                else{
                    GetResultEventNotificationJSON notFound = new GetResultEventNotificationJSON( 203, "Not found Events", null);
                    session.getAsyncRemote().sendObject(notFound);
                }
            }
            catch(EJBException ex){
                GetResultEventNotificationJSON notFound = new GetResultEventNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
            catch(NoResultException ex){
                GetResultEventNotificationJSON notFound = new GetResultEventNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }
    }
}
