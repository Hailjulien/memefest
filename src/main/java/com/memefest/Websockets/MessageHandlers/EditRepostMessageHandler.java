package com.memefest.Websockets.MessageHandlers;
import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.RepostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.EditRepostJSON;
import com.memefest.Websockets.JSON.EditResultRepostJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;

public class EditRepostMessageHandler implements MessageHandler.Whole<EditRepostJSON>{

   private PostOperations postOps;
   private Session session;

   public EditRepostMessageHandler(PostOperations postOps, Session session){
       this.postOps = postOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
   @Override
   public  void onMessage(EditRepostJSON repostEdit){
        Set<RepostJSON> reposts = repostEdit.getPosts();
        EditResultRepostJSON successEdits = new EditResultRepostJSON(null, "success" ,200);
        EditResultRepostJSON failureEdits = new EditResultRepostJSON(null, "Could not edit" ,203);
        for(RepostJSON post : reposts){
            try{
                try{
                    postOps.editRepost(post);
                    RepostJSON postEntity = postOps.getRepostInfo(post);
                    Set<RepostJSON> resCats = successEdits.getReposts();
                    if(resCats == null)
                        resCats = new HashSet<RepostJSON>();
                    resCats.add(postEntity);
                    successEdits.setReposts(resCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<RepostJSON> resCats = failureEdits.getReposts();
                    if(resCats == null)
                        resCats = new HashSet<RepostJSON>();
                    resCats.add(post);
                    failureEdits.setReposts(resCats);
                }
                catch (RollbackException ex) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<RepostJSON> resCats = failureEdits.getReposts();
                    if(resCats == null)
                        resCats = new HashSet<RepostJSON>();
                    resCats.add(post);
                    failureEdits.setReposts(resCats);
                }
            }
            catch(EJBException ex){                    
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<RepostJSON> resCats = failureEdits.getReposts();
                if(resCats == null)
                    resCats = new HashSet<RepostJSON>();
                resCats.add(post);
                failureEdits.setReposts(resCats);
            }
                    
        }
        if(successEdits.getReposts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getReposts() != null)
            session.getAsyncRemote().sendObject(failureEdits);                   
   }
}

