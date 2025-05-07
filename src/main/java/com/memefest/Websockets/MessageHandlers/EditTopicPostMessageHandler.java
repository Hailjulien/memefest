package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.TopicPostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.EditResultTopicPostJSON;
import com.memefest.Websockets.JSON.EditTopicPostJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;


public class EditTopicPostMessageHandler implements MessageHandler.Whole<EditTopicPostJSON>{

   private PostOperations postOps;
   private Session session;

   public EditTopicPostMessageHandler(PostOperations postOps, Session session){
       this.postOps = postOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
   @Override
   public  void onMessage(EditTopicPostJSON editTopic){
        Set<TopicPostJSON> topicPosts = editTopic.getTopicPosts();
        EditResultTopicPostJSON successEdits = new EditResultTopicPostJSON(200, "Success", null);
        EditResultTopicPostJSON failureEdits = new EditResultTopicPostJSON(203, "Could not edit", null);
        for(TopicPostJSON topicPost : topicPosts){
            try{
                try{
                    postOps.editTopicPost((TopicPostJSON)topicPost);
                    TopicPostJSON topicPostEntity = postOps.getTopicPostInfo(topicPost);
                    Set<TopicPostJSON> resCats = successEdits.getTopicPosts();
                    if(resCats == null)
                        resCats = new HashSet<TopicPostJSON>();
                    resCats.add(topicPostEntity);
                    successEdits.setTopicPosts(resCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<TopicPostJSON> resCats = failureEdits.getTopicPosts();
                    if(resCats == null)
                        resCats = new HashSet<TopicPostJSON>();
                    resCats.add(topicPost);
                    failureEdits.setTopicPosts(resCats);
                }
                catch (RollbackException ex) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<TopicPostJSON> resCats = failureEdits.getTopicPosts();
                    if(resCats == null)
                        resCats = new HashSet<TopicPostJSON>();
                    resCats.add(topicPost);
                    failureEdits.setTopicPosts(resCats);
                }
            }
            catch(EJBException ex){                    
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<TopicPostJSON> resCats = failureEdits.getTopicPosts();
                if(resCats == null)
                    resCats = new HashSet<TopicPostJSON>();
                resCats.add(topicPost);
                failureEdits.setTopicPosts(resCats);
            }
                    
        }
        if(successEdits.getTopicPosts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getTopicPosts() != null)
            session.getAsyncRemote().sendObject(failureEdits);           
   }
}   
