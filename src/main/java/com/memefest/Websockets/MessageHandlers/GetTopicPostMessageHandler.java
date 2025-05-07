package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.TopicPostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.GetTopicPostJSON;
import com.memefest.Websockets.JSON.GetResultTopicPostJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetTopicPostMessageHandler implements MessageHandler.Whole<GetTopicPostJSON>{

    private PostOperations postOps;
    private Session session;
 
    public GetTopicPostMessageHandler(PostOperations postOps, Session session){
        this.postOps = postOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetTopicPostJSON getTopicPost){
        GetResultTopicPostJSON successEdits = new GetResultTopicPostJSON(200, "Success",null);
        GetResultTopicPostJSON failureEdits = new GetResultTopicPostJSON(203, "Could not edit", null);

           for(TopicPostJSON topicPost : getTopicPost.getTopicPosts()){
                try{
                    TopicPostJSON topicEntity = postOps.getTopicPostInfo(topicPost);
                    if(topicEntity!= null){
                        Set<TopicPostJSON> topicCats = successEdits.getTopicPosts();
                        topicCats.add(topicEntity);
                        successEdits.setTopicPosts(topicCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<TopicPostJSON> topicCats = failureEdits.getTopicPosts();
                    if(topicCats == null)
                        topicCats = new HashSet<TopicPostJSON>();
                    topicCats.add(topicPost);
                    failureEdits.setTopicPosts(topicCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<TopicPostJSON> topicCats = failureEdits.getTopicPosts();
                    if(topicCats == null)
                        topicCats = new HashSet<TopicPostJSON>();
                    topicCats.add(topicPost);
                    failureEdits.setTopicPosts(topicCats);
                }
        }
        if(successEdits.getTopicPosts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getTopicPosts() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }   
}
