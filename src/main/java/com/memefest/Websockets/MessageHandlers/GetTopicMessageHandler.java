package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.Services.TopicOperations;
import com.memefest.Websockets.JSON.GetResultTopicJSON;
import com.memefest.Websockets.JSON.GetTopicJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetTopicMessageHandler implements MessageHandler.Whole<GetTopicJSON>{

    private TopicOperations topicOps;
    private Session session;
 
    public GetTopicMessageHandler(TopicOperations topicOps, Session session){
        this.topicOps = topicOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetTopicJSON getTopic){
        GetResultTopicJSON successEdits = new GetResultTopicJSON(200, "Success",null);
        GetResultTopicJSON failureEdits = new GetResultTopicJSON(203, "Could not edit", null);

           for(TopicJSON topic : getTopic.getTopics()){
                try{
                    TopicJSON topicEntity = topicOps.getTopicInfo(topic);
                    if(topicEntity!= null){
                        Set<TopicJSON> topicCats = successEdits.getTopics();
                        topicCats.add(topicEntity);
                        successEdits.setTopics(topicCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<TopicJSON> topicCats = failureEdits.getTopics();
                    if(topicCats == null)
                        topicCats = new HashSet<TopicJSON>();
                    topicCats.add(topic);
                    failureEdits.setTopics(topicCats);
                } 
                catch (NoResultException e) { 
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<TopicJSON> topicCats = failureEdits.getTopics();
                    if(topicCats == null)
                        topicCats = new HashSet<TopicJSON>();
                    topicCats.add(topic);
                    failureEdits.setTopics(topicCats);
                }
        }
        if(successEdits.getTopics() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getTopics() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }   
}
