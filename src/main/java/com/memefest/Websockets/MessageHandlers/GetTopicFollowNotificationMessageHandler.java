package com.memefest.Websockets.MessageHandlers;

import java.util.Set;
import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.GetResultTopicFollowNotificationJSON;
import com.memefest.Websockets.JSON.GetTopicFollowNotificationJSON;
import com.memefest.Websockets.JSON.TopicFollowNotificationJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetTopicFollowNotificationMessageHandler implements MessageHandler.Whole<com.memefest.Websockets.JSON.GetTopicFollowNotificationJSON>{

    private NotificationOperations notOps;
    private Session session;
 
    public GetTopicFollowNotificationMessageHandler(NotificationOperations notOps, Session session){
        this.notOps = notOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetTopicFollowNotificationJSON getEvent){
        for(TopicFollowNotificationJSON event : getEvent.getTopicFollowNotifications()){
            try{
                Set<TopicFollowNotificationJSON> events = notOps.getTopicFollowNotificationInfo(event);
                if(events != null){
                    GetResultTopicFollowNotificationJSON found = new GetResultTopicFollowNotificationJSON(200, "Found events", null);
                    found.setTopicFollowNotifications(events);
                    session.getAsyncRemote().sendObject(found);
                }
                else{
                    GetResultTopicFollowNotificationJSON notFound = new GetResultTopicFollowNotificationJSON( 203, "Not found Events", null);
                    session.getAsyncRemote().sendObject(notFound);
                }
            }
            catch(EJBException ex){
                GetResultTopicFollowNotificationJSON notFound = new GetResultTopicFollowNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
            catch(NoResultException ex){
                GetResultTopicFollowNotificationJSON notFound = new GetResultTopicFollowNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }
    }
}
