package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.GetResultTopicPostNotificationJSON;
import com.memefest.Websockets.JSON.GetTopicPostNotificationJSON;
import com.memefest.Websockets.JSON.TopicPostNotificationJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetTopicPostNotificationMessageHandler implements MessageHandler.Whole<GetTopicPostNotificationJSON>{

    private NotificationOperations notOps;
    private Session session;
 
    public GetTopicPostNotificationMessageHandler(NotificationOperations notOps, Session session){
        this.notOps = notOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetTopicPostNotificationJSON getEvent){        
        for(TopicPostNotificationJSON topic : getEvent.getTopicPostNotifications()){
            try{
                Set<TopicPostNotificationJSON> topics = notOps.getTopicPostNotificationInfo(topic);
                if(topics != null){
                    GetResultTopicPostNotificationJSON found = new GetResultTopicPostNotificationJSON(200, "Found events", null);
                    found.setTopicPostNotifications(topics);
                    session.getAsyncRemote().sendObject(found);
                }
                else{
                    GetResultTopicPostNotificationJSON notFound = new GetResultTopicPostNotificationJSON( 203, "Not found Events", null);
                    session.getAsyncRemote().sendObject(notFound);
                }
            }
            catch(EJBException ex){
                GetResultTopicPostNotificationJSON notFound = new GetResultTopicPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
            catch(NoResultException ex){
                GetResultTopicPostNotificationJSON notFound = new GetResultTopicPostNotificationJSON( 203, "Not found Events", null);
                session.getAsyncRemote().sendObject(notFound);
            }
        }
    }
}