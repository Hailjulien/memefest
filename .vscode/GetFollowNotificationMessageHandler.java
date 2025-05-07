package com.memefest.Websockets.MessageHandlers;

import java.util.Set;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.EventNotificationJSON;
import com.memefest.Websockets.JSON.EventPostNotificationJSON;
import com.memefest.Websockets.JSON.FollowNotificationJSON;
import com.memefest.Websockets.JSON.GetEventJSON;
import com.memefest.Websockets.JSON.GetEventNotificationJSON;
import com.memefest.Websockets.JSON.GetEventPostNotificationJSON;
import com.memefest.Websockets.JSON.GetFollowNotificationJSON;
import com.memefest.Websockets.JSON.GetPostNotificationJSON;
import com.memefest.Websockets.JSON.GetResultEventJSON;
import com.memefest.Websockets.JSON.GetResultEventNotificationJSON;
import com.memefest.Websockets.JSON.GetResultEventPostNotificationJSON;
import com.memefest.Websockets.JSON.PostNotificationJSON;

import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetFollowNotificationMessageHandler implements MessageHandler.Whole<GetFollowNotificationJSON  >{

    private NotificationOperations notOps;
    private Session session;
 
    public GetFollowNotificationMessageHandler(NotificationOperations notOps, Session session){
        this.notOps = notOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetFollowNotificationJSON getEvent){
        for(FollowNotificationJSON event : getEvent.getFollowNotifications()){
            Set<FollowNotificationJSON> events = notOps.getUserFollowNotificationInfo(event);
            if(events != null){
                GetResultEventPostNotificationJSON found = new GetResultEventPostNotificationJSON(200, "Found events", null);
                session.getAsyncRemote().sendObject(found);
            }
            else{
                GetResultEventPostNotificationJSON notFound = new GetResultEventPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }
        
        
    }
}