package com.memefest.Websockets;

import com.memefest.Services.CategoryOperations;
import com.memefest.Services.EventOperations;
import com.memefest.Services.FeedsOperations;
import com.memefest.Services.NotificationOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Services.UserOperations;
import com.memefest.Websockets.MessageHandlers.EditAdminMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditCategoryMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditEventMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditEventNotificationMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditEventPostMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditEventPostNotificationMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditPostMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditPostNotificationMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditRepostMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditTopicFollowNotificationMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditTopicMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditTopicPostMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditTopicPostNotificationMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditUserFollowNotificationMessageHandler;
import com.memefest.Websockets.MessageHandlers.EditUserMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetAdminMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetCategoryMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetEventMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetEventNotificationMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetEventPostMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetEventPostNotificationMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetPostMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetRepostMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetTopicFollowNotificationMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetTopicMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetTopicPostMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetUserFollowNotificationMessageHandler;
import com.memefest.Websockets.MessageHandlers.GetUserMessageHandler;

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
        session.addMessageHandler(new EditUserMessageHandler(userOperations, session));
        session.addMessageHandler(new EditPostMessageHandler(postOperations, session));
        session.addMessageHandler(new EditAdminMessageHandler(userOperations, session));
        session.addMessageHandler(new EditCategoryMessageHandler(catOps, session));
        session.addMessageHandler(new EditEventMessageHandler(eventOps, session));
        session.addMessageHandler(new EditEventPostMessageHandler(postOperations, session));
        session.addMessageHandler(new EditTopicFollowNotificationMessageHandler(notOps, session));
        session.addMessageHandler(new EditEventNotificationMessageHandler(notOps, session));
        session.addMessageHandler(new EditEventPostNotificationMessageHandler(notOps, session));
        session.addMessageHandler(new EditPostNotificationMessageHandler(notOps, session));
        session.addMessageHandler(new EditRepostMessageHandler(postOperations, session));
        session.addMessageHandler(new EditTopicMessageHandler(topicOps, session));
        session.addMessageHandler(new EditTopicFollowNotificationMessageHandler(notOps, session));
        session.addMessageHandler(new EditTopicPostMessageHandler(postOperations, session));
        session.addMessageHandler(new EditTopicPostNotificationMessageHandler(notOps, session));
        session.addMessageHandler(new EditUserFollowNotificationMessageHandler(notOps, session));
        session.addMessageHandler(new GetAdminMessageHandler(userOperations, session));
        session.addMessageHandler(new GetCategoryMessageHandler(catOps, session));
        session.addMessageHandler(new GetEventMessageHandler(eventOps, session));
        session.addMessageHandler(new GetEventPostMessageHandler(postOperations, session));
        session.addMessageHandler(new GetEventNotificationMessageHandler(notOps, session));
        session.addMessageHandler(new GetEventPostNotificationMessageHandler(notOps, session));
        session.addMessageHandler(new GetPostMessageHandler(postOperations, session));
        session.addMessageHandler(new GetRepostMessageHandler(postOperations, session)); 
        session.addMessageHandler(new GetTopicFollowNotificationMessageHandler(notOps, session));
        session.addMessageHandler(new GetTopicMessageHandler(topicOps, session));
        session.addMessageHandler(new GetTopicPostMessageHandler(postOperations, session));
        session.addMessageHandler(new GetUserFollowNotificationMessageHandler(notOps, session));
        session.addMessageHandler(new GetUserMessageHandler(userOperations, session)); 
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
