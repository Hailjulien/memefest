package com.memefest.Websockets;

import com.memefest.Services.FeedsOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.Impl.FeedsEndPointService;
import com.memefest.Websockets.MessageHandlers.EditPostMessageHandler;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.PostActivate;
import jakarta.ejb.Stateful;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;

@Stateful(name = "feedsEndpoint")
public class FeedsEndpoint extends Endpoint{
    
    
    @EJB
    private FeedsOperations feedsServer;

    @EJB
    private PostOperations postOperations;
    
    
    

    @PostConstruct
    @PostActivate
    public void init() {
        
    }

    @Override
    public void onOpen(Session session, EndpointConfig config){
        //notifiacation about user joining room
        //timer to monitor time spent for each student in session
        //int topicId = Integer.valueOf(session.getPathParameters().get("topicId"));
        /*topic = new TopicJSON(topicId, null, null, null, null, null);
            topic = topicOps.getTopicInfo(topic);
        
        this.peers.add(session);
        session.addMessageHandler(new MessageHandler.Whole<PostJSON>() {
            @Override
            public void onMessage(PostJSON message) {
                broadcast(session, message);
            }
        });
        */
        feedsServer.addClient(session);
        session.addMessageHandler(new EditPostMessageHandler(postOperations, session));    
        //fetch fresh relevant unseen content(Topics with most comments, topics subscribed by user) from database affiliated with the user

    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        feedsServer.removeClient(session);
        try {
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    //create a notification message to show logged in user
 
}
