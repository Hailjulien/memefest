package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.Services.TopicOperations;
import com.memefest.Websockets.JSON.EditResultTopicJSON;
import com.memefest.Websockets.JSON.EditTopicJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
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
            try{
                try{
                    topicOps.editTopic(topic);
                    TopicJSON topicEntity = topicOps.getTopicInfo(topic);
                    Set<TopicJSON> resCats = resultTopic.getTopics();
                    if(resCats == null)
                        resCats = new HashSet<TopicJSON>();
                    resCats.add(topicEntity);
                    resultTopic.setTopics(resCats);
                } 
                catch (NoResultException e) {
                    notEditableTopic.setResultMessage(resultTopic.getResultMessage() + "," + e.getMessage());
                    Set<TopicJSON> resCats = resultTopic.getTopics();
                    if(resCats == null)
                        resCats = new HashSet<TopicJSON>();
                    resCats.add(topic);
                    notEditableTopic.setTopics(resCats);
                }
                catch (RollbackException ex) {
                    notEditableTopic.setResultMessage(notEditableTopic.getResultMessage() + "," + ex.getMessage());
                    Set<TopicJSON> resCats = notEditableTopic.getTopics();
                    if(resCats == null)
                        resCats = new HashSet<TopicJSON>();
                    resCats.add(topic);
                    notEditableTopic.setTopics(resCats);
                }
            }
            catch(EJBException ex){                    
                notEditableTopic.setResultMessage(notEditableTopic.getResultMessage() + "," + ex.getMessage());
                Set<TopicJSON> resCats = notEditableTopic.getTopics();
                if(resCats == null)
                    resCats = new HashSet<TopicJSON>();
                resCats.add(topic);
                notEditableTopic.setTopics(resCats);
            }
                    
        }
        if(resultTopic.getTopics() != null)
            session.getAsyncRemote().sendObject(resultTopic);
        if(notEditableTopic.getTopics() != null)
            session.getAsyncRemote().sendObject(notEditableTopic);                   
   }
}   

