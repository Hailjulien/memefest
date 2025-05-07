package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.GetPostJSON;
import com.memefest.Websockets.JSON.GetResultPostJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetPostMessageHandler implements MessageHandler.Whole<GetPostJSON>{

    private PostOperations postOps;
    private Session session;
 
    public GetPostMessageHandler(PostOperations postOps, Session session){
        this.postOps = postOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetPostJSON getPost){    
        GetResultPostJSON successEdits = new GetResultPostJSON(200, "Success",null);
        GetResultPostJSON failureEdits = new GetResultPostJSON(203, "Could not edit", null);

           for(PostJSON post : getPost.getPosts()){
                try{
                    PostJSON postEntity = postOps.getPostInfo(post);
                    if(postEntity!= null){
                        Set<PostJSON> postCats = successEdits.getPostResult();
                        postCats.add(postEntity);
                        successEdits.setPostResult(postCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<PostJSON> postCats = failureEdits.getPostResult();
                    if(postCats == null)
                        postCats = new HashSet<PostJSON>();
                    postCats.add(post);
                    failureEdits.setPostResult(postCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<PostJSON> postCats = failureEdits.getPostResult();
                    if(postCats == null)
                        postCats = new HashSet<PostJSON>();
                    postCats.add(post);
                    failureEdits.setPostResult(postCats);
                }
        }
        if(successEdits.getPostResult() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getPostResult() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }
}