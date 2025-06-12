package com.memefest.Websockets.MessageHandlers;

import java.time.LocalDateTime;
import java.util.Map;

import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Websockets.JSON.EditRepostJSON;
import com.memefest.Websockets.JSON.EditScheduledEventJSON;
import com.memefest.Websockets.JSON.EditScheduledTopicJSON;
import com.memefest.Websockets.JSON.GetPostReplysJSON;
import com.memefest.Websockets.JSON.GetResultScheduledEventJSON;
import com.memefest.Websockets.JSON.GetResultScheduledTopicJSON;
import com.memefest.Websockets.JSON.GetScheduledEventJSON;
import com.memefest.Websockets.JSON.GetScheduledTopicJSON;

import jakarta.ejb.EJBException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetScheduledTopicMessageHandler implements MessageHandler.Whole<GetScheduledTopicJSON>{
    
    private TopicOperations topicOps;
    private Session session; 

    public GetScheduledTopicMessageHandler(TopicOperations topicOps, Session session){
        this.topicOps = topicOps;
        this.session = session;
    }   

    @Override
    public  void onMessage(GetScheduledTopicJSON repostEdit){
        for(TopicJSON topic : repostEdit.getTopics()){
            try{
                Map<TopicJSON, LocalDateTime> eventTimes = topicOps.getScheduledTopics(topic);
                GetResultScheduledTopicJSON success = new GetResultScheduledTopicJSON(200, "Successful Results", eventTimes);
                session.getAsyncRemote().sendObject(success);
            }
            catch(EJBException ex){
                GetResultScheduledEventJSON  failed = new GetResultScheduledEventJSON(203, ex.getMessage(), null);
                session.getAsyncRemote().sendObject(failed);
            }
        }   
    }
}
