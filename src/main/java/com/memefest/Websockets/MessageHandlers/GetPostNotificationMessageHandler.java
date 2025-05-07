package com.memefest.Websockets.MessageHandlers;

import java.util.Set;
import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.GetPostNotificationJSON;
import com.memefest.Websockets.JSON.GetResultPostNotificationJSON;
import com.memefest.Websockets.JSON.PostNotificationJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetPostNotificationMessageHandler implements MessageHandler.Whole<GetPostNotificationJSON>{

    private NotificationOperations notOps;
    private Session session;
 
    public GetPostNotificationMessageHandler(NotificationOperations notOps, Session session){
        this.notOps = notOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetPostNotificationJSON getEvent){
        for(PostNotificationJSON event : getEvent.getPostNotifications()){
            try{
                Set<PostNotificationJSON> events = notOps.getPostNotificationInfo(event);
                if(events != null){
                    GetResultPostNotificationJSON found = new GetResultPostNotificationJSON(200, "Found events", null);
                    found.setPostNotifications(events);
                    session.getAsyncRemote().sendObject(found);
                }
                else{
                    GetResultPostNotificationJSON notFound = new GetResultPostNotificationJSON( 203, "Not found Events", null);
                    session.getAsyncRemote().sendObject(notFound);
                }
            }
            catch(EJBException ex){
                GetResultPostNotificationJSON notFound = new GetResultPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
            catch(NoResultException ex){
                GetResultPostNotificationJSON notFound = new GetResultPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }    
    }
}