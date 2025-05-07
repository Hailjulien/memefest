package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.GetResultUserFollowNotificationJSON;
import com.memefest.Websockets.JSON.GetUserFollowNotificationJSON;
import com.memefest.Websockets.JSON.UserFollowNotificationJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetUserFollowNotificationMessageHandler implements MessageHandler.Whole<GetUserFollowNotificationJSON>{

    private NotificationOperations notOps;
    private Session session;
 
    public GetUserFollowNotificationMessageHandler(NotificationOperations notOps, Session session){
        this.notOps = notOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetUserFollowNotificationJSON getEvent){
        for(UserFollowNotificationJSON event : getEvent.getUserFollowNotifications()){
            try{
                Set<UserFollowNotificationJSON> events = notOps.getUserFollowNotificationInfo(event);
                if(events != null){
                    GetResultUserFollowNotificationJSON found = new GetResultUserFollowNotificationJSON(200, "Found events", null);
                    found.setUserFollowNotifications(events);
                    session.getAsyncRemote().sendObject(found);
                }
                else{
                    GetResultUserFollowNotificationJSON notFound = new GetResultUserFollowNotificationJSON( 203, "Not found Events", null);
                    session.getAsyncRemote().sendObject(notFound);
                }
            }
            catch(EJBException ex){
                GetResultUserFollowNotificationJSON notFound = new GetResultUserFollowNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
            catch(NoResultException ex){
                GetResultUserFollowNotificationJSON notFound = new GetResultUserFollowNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }
        
    }
}


