package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.Services.TopicOperations;
import com.memefest.Websockets.JSON.SearchResultTopicJSON;
import com.memefest.Websockets.JSON.SearchTopicJSON;

import jakarta.websocket.Session;
import jakarta.websocket.MessageHandler;

public class SearchTopicMessageHandler implements MessageHandler.Whole<SearchTopicJSON>{

   private TopicOperations topicOps;
   private Session session;

   public SearchTopicMessageHandler(TopicOperations topicOps, Session session){
       this.topicOps = topicOps;
       this.session = session;
   }

   @Override
   public  void onMessage(SearchTopicJSON searchCommand){
        TopicJSON topic = searchCommand.getTopic();
        Set<TopicJSON> topics = topicOps.searchTopic(topic);
        //add customisation filter according to users tastes here
        SearchResultTopicJSON result = new SearchResultTopicJSON(null, 0, null);
        if(topics.size() == 0){
            result.setResultId(204);
            result.setResultMessage("No results");
        }
        else{
            result.setResultId(200);
            result.setResultMessage("Success");
            result.setTopics(topics);
        }
        session.getAsyncRemote().sendObject(result);
   }     

}
