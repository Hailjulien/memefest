package com.memefest.Websockets.MessageHandlers;


import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.EditPostJSON;
import com.memefest.Websockets.JSON.EditResultPostJSON;
import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;

public class EditPostMessageHandler implements MessageHandler.Whole<EditPostJSON>{

   private PostOperations postOps;
   private Session session;

   public EditPostMessageHandler(PostOperations postOps, Session session){
       this.postOps = postOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
    @Override
   public  void onMessage(EditPostJSON postEdit){
        Set<PostJSON> posts = postEdit.getPosts();
        EditResultPostJSON successEdits = new EditResultPostJSON(null ,200, "success");
        EditResultPostJSON failureEdits = new EditResultPostJSON(null, 203, "Could not edit");
        for(PostJSON post : posts){
            try{
                try{
                    postOps.editPost((PostJSON)post);
                    PostJSON postEntity = postOps.getPostInfo(post);
                    Set<PostJSON> resCats = successEdits.getPosts();
                    if(resCats == null)
                        resCats = new HashSet<PostJSON>();
                    resCats.add(postEntity);
                    successEdits.setPosts(resCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<PostJSON> resCats = failureEdits.getPosts();
                    if(resCats == null)
                        resCats = new HashSet<PostJSON>();
                    resCats.add(post);
                    failureEdits.setPosts(resCats);
                }
                catch (RollbackException ex) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<PostJSON> resCats = failureEdits.getPosts();
                    if(resCats == null)
                        resCats = new HashSet<PostJSON>();
                    resCats.add(post);
                    failureEdits.setPosts(resCats);
                }
            }
            catch(EJBException ex){                    
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<PostJSON> resCats = failureEdits.getPosts();
                if(resCats == null)
                    resCats = new HashSet<PostJSON>();
                resCats.add(post);
                failureEdits.setPosts(resCats);
            }
                    
        }
        if(successEdits.getPosts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getPosts() != null)
            session.getAsyncRemote().sendObject(failureEdits);                   
   }
   
}
