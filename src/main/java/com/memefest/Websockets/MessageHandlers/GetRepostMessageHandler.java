package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.RepostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.GetRepostJSON;
import com.memefest.Websockets.JSON.GetResultRepostJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetRepostMessageHandler implements MessageHandler.Whole<GetRepostJSON>{

    private PostOperations postOps;
    private Session session;
 
    public GetRepostMessageHandler(PostOperations postOps, Session session){
        this.postOps = postOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetRepostJSON getRepost){
        GetResultRepostJSON notFound = new GetResultRepostJSON( 203, "Not found Event Posts", null);
        GetResultRepostJSON found = new GetResultRepostJSON(200, "Found Event Posts", null);
        for(RepostJSON rePost : getRepost.getReposts()){
            rePost = postOps.getRepostInfo(rePost);
            if(rePost != null){
                Set<RepostJSON> reposts = found.getReposts();
                reposts.add(rePost);
                found.setReposts(reposts);
            }
            else{
                Set<RepostJSON> events = notFound.getReposts();
                events.add(rePost);
                notFound.setReposts(events);
            }
        }
        session.getAsyncRemote().sendObject(found);
        session.getAsyncRemote().sendObject(notFound);

        GetResultRepostJSON successEdits = new GetResultRepostJSON(200, "Success",null);
        GetResultRepostJSON failureEdits = new GetResultRepostJSON(203, "Could not edit", null);

           for(RepostJSON repost : getRepost.getReposts()){
                try{
                    RepostJSON repostEntity = postOps.getRepostInfo(repost);
                    if(repostEntity!= null){
                        Set<RepostJSON> postCats = successEdits.getReposts();
                        postCats.add(repostEntity);
                        successEdits.setReposts(postCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<RepostJSON> postCats = failureEdits.getReposts();
                    if(postCats == null)
                        postCats = new HashSet<RepostJSON>();
                    postCats.add(repost);
                    failureEdits.setReposts(postCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<RepostJSON> postCats = failureEdits.getReposts();
                    if(postCats == null)
                        postCats = new HashSet<RepostJSON>();
                    postCats.add(repost);
                    failureEdits.setReposts(postCats);
                }
        }
        if(successEdits.getReposts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getReposts() != null)
            session.getAsyncRemote().sendObject(failureEdits);
    }   
}
