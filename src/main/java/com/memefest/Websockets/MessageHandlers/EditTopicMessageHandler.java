package com.memefest.Websockets.MessageHandlers;

import java.util.Set;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.Services.TopicOperations;
import com.memefest.Websockets.JSON.EditResultTopicJSON;
import com.memefest.Websockets.JSON.EditTopicJSON;

import jakarta.websocket.Session;
import jakarta.websocket.MessageHandler;

public class EditTopicMessageHandler implements MessageHandler.Whole<EditTopicJSON>{

   private TopicOperations topicOps;
   private Session session;

   public EditTopicMessageHandler(TopicOperations topicOps, Session session){
       this.topicOps = topicOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
   @Override
   public  void onMessage(EditTopicJSON editTopic){
        Set<TopicJSON> topics = editTopic.getTopics();
        EditResultTopicJSON resultTopic = new EditResultTopicJSON(null,200, "success");
        EditResultTopicJSON notEditableTopic = new EditResultTopicJSON(null,203, "Could not edit");

        for(TopicJSON topic : topics){
            topicOps.editTopic(topic);
            TopicJSON topicEntity = topicOps.getTopicInfo(topic);
            if(topicEntity!= null){
                Set<TopicJSON> resTopics = resultTopic.getTopics();
                resTopics.add(topicEntity);
                resultTopic.setTopics(resTopics);
            }
            else{
                Set<TopicJSON> resTopics = notEditableTopic.getTopics();
                resTopics.add(topicEntity);
                notEditableTopic.setTopics(resTopics);
            }
        }
        session.getAsyncRemote().sendObject(resultTopic);
        session.getAsyncRemote().sendObject(notEditableTopic);
   }
}   

