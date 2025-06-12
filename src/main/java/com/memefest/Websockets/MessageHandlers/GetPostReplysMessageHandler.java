package com.memefest.Websockets.MessageHandlers;



import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Websockets.JSON.EditRepostJSON;
import com.memefest.Websockets.JSON.EditScheduledEventJSON;
import com.memefest.Websockets.JSON.EditScheduledTopicJSON;
import com.memefest.Websockets.JSON.GetPostReplysJSON;
import com.memefest.Websockets.JSON.GetResultPostWithReplyJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetPostReplysMessageHandler implements MessageHandler.Whole<GetPostReplysJSON>{
    
    private PostOperations postOps;
    private Session session; 

    public GetPostReplysMessageHandler(PostOperations postOps, Session session){
        this.postOps = postOps;
        this.session = session;
    }   

    @Override
    public  void onMessage(GetPostReplysJSON repostEdit){
        GetResultPostWithReplyJSON success = new GetResultPostWithReplyJSON(200, "Found Results", null);
        GetResultPostWithReplyJSON failed = new GetResultPostWithReplyJSON(203, "Not Found Results", null);
        
        for(PostWithReplyJSON postWithReply : repostEdit.getPostWithReplys()){
            try{    
                postWithReply = postOps.getPostWithReplyInfo(postWithReply);
                if(postWithReply!= null){
                    Set<PostWithReplyJSON> postCats = success.getPostWithReplies();
                    if(postCats == null)
                        postCats = new HashSet<PostWithReplyJSON>();
                    postCats.add(postWithReply);
                    success.setPostWithReplies(postCats);
                }
            } 
            catch(NoResultException ex){    
                if(postWithReply!= null){
                    Set<PostWithReplyJSON> postCats = failed.getPostWithReplies();
                    if(postCats == null)
                        postCats = new HashSet<PostWithReplyJSON>();
                    postCats.add(postWithReply);
                    failed.setPostWithReplies(postCats);
                }
            }
            catch(EJBException ex){
                if(postWithReply!= null){
                    Set<PostWithReplyJSON> postCats = failed.getPostWithReplies();
                    if(postCats == null)
                        postCats = new HashSet<PostWithReplyJSON>();
                    postCats.add(postWithReply);
                    failed.setPostWithReplies(postCats);
                }
            }
        }
        if(failed.getPostWithReplies() != null)
            session.getAsyncRemote().sendObject(failed);
        if(success.getPostWithReplies() != null)
            session.getAsyncRemote().sendObject(success);
    }   
}
