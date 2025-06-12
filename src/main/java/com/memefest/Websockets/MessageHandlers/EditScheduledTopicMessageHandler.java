package com.memefest.Websockets.MessageHandlers;

import java.time.LocalDateTime;
import java.util.Map;

import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Websockets.JSON.EditRepostJSON;
import com.memefest.Websockets.JSON.EditResultScheduledEventJSON;
import com.memefest.Websockets.JSON.EditResultScheduledTopicJSON;
import com.memefest.Websockets.JSON.EditScheduledEventJSON;
import com.memefest.Websockets.JSON.EditScheduledTopicJSON;

import jakarta.ejb.EJBException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class EditScheduledTopicMessageHandler implements MessageHandler.Whole<EditScheduledTopicJSON>{
    
    private TopicOperations topicOps;
    private Session session; 

    public EditScheduledTopicMessageHandler(TopicOperations topicOps, Session session){
        this.topicOps = topicOps;
        this.session = session;
    }   

    @Override
    public  void onMessage(EditScheduledTopicJSON repostEdit){
        EditResultScheduledTopicJSON successful = new EditResultScheduledTopicJSON(200, "Found Results", null);
        EditResultScheduledTopicJSON failed = new EditResultScheduledTopicJSON(203, "Not edited", null);
        Map<TopicJSON, LocalDateTime> topicTimes = repostEdit.getTopicTimes();
        try{
            topicOps.editScheduledTopic(topicTimes);
            successful.setTopicTimes(topicTimes);
        }
        catch(EJBException ex){
            failed.setTopicTimes(topicTimes);    
        }
        if(successful.getTopicTimes() != null){
            session.getAsyncRemote().sendObject(successful);
        }
        if(failed.getTopicTimes() != null){
            session.getAsyncRemote().sendObject(failed);
        }
   }
}
