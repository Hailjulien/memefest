package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.EventPostNotificationJSON;
import com.memefest.Websockets.JSON.GetEventPostNotificationJSON;
import com.memefest.Websockets.JSON.GetResultEventPostNotificationJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetEventPostNotificationMessageHandler implements MessageHandler.Whole<GetEventPostNotificationJSON>{

    private NotificationOperations notOps;
    private Session session;
 
    public GetEventPostNotificationMessageHandler(NotificationOperations notOps, Session session){
        this.notOps = notOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetEventPostNotificationJSON getEvent){
        for(EventPostNotificationJSON event : getEvent.getEventPostNotifications()){
            try{
                Set<EventPostNotificationJSON> events = notOps.getEventPostNotificationInfo(event);
                if(events != null){
                    GetResultEventPostNotificationJSON found = new GetResultEventPostNotificationJSON(200, "Found events", null);
                    found.setEventPostNotifications(events);
                    session.getAsyncRemote().sendObject(found);
                }
                else{
                    GetResultEventPostNotificationJSON notFound = new GetResultEventPostNotificationJSON( 203, "Not found Events", null);
                    session.getAsyncRemote().sendObject(notFound);
                }
            }
            catch(EJBException ex){
                GetResultEventPostNotificationJSON notFound = new GetResultEventPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
            catch(NoResultException ex){
                GetResultEventPostNotificationJSON notFound = new GetResultEventPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }
    }
}