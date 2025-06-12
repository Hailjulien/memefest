package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.EditPostWithReplyJSON;
import com.memefest.Websockets.JSON.EditResultPostJSON;
import com.memefest.Websockets.JSON.EditResultPostWithReplyJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class EditPostWithReplyMessageHandler implements MessageHandler.Whole<EditPostWithReplyJSON>{
    

    private PostOperations postOps;
    private Session session;

    public EditPostWithReplyMessageHandler(PostOperations postOps, Session session){
       this.postOps = postOps;
       this.session = session;  
    }


    @Override
    public void onMessage(EditPostWithReplyJSON postWithReplyEdit){
        Set<PostWithReplyJSON> posts = postWithReplyEdit.getPostsWithReplys();
        EditResultPostWithReplyJSON successEdits = new EditResultPostWithReplyJSON(200 , "success", null);
        EditResultPostWithReplyJSON failureEdits = new EditResultPostWithReplyJSON(203, "Could not edit", null);
        for(PostWithReplyJSON post : posts){
            try{
                postOps.editPostWithReply(post);
                try{
                    PostWithReplyJSON postEntity = postOps.getPostWithReplyInfo(post);
                    Set<PostWithReplyJSON> resCats = successEdits.getPostWithReplies();
                    if(resCats == null)
                        resCats = new HashSet<PostWithReplyJSON>();
                    resCats.add(postEntity);
                    successEdits.setPostWithReplies(resCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<PostWithReplyJSON> resCats = failureEdits.getPostWithReplies();
                    if(resCats == null)
                        resCats = new HashSet<PostWithReplyJSON>();
                    resCats.add(post);
                    failureEdits.setPostWithReplies(resCats);
                }
                catch (RollbackException ex) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<PostWithReplyJSON> resCats = failureEdits.getPostWithReplies();
                    if(resCats == null)
                        resCats = new HashSet<PostWithReplyJSON>();
                    resCats.add(post);
                    failureEdits.setPostWithReplies(resCats);
                }
            }
            catch(EJBException ex){                    
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<PostWithReplyJSON> resCats = failureEdits.getPostWithReplies();
                if(resCats == null)
                    resCats = new HashSet<PostWithReplyJSON>();
                resCats.add(post);
                failureEdits.setPostWithReplies(resCats);
            }
                    
        }
        if(successEdits.getPostWithReplies() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getPostWithReplies() != null)
            session.getAsyncRemote().sendObject(failureEdits);                   
   }
}
