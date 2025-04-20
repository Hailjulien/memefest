package com.memefest.Websockets.MessageHandlers;


import java.util.Set;

import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.EditPostJSON;
import com.memefest.Websockets.JSON.EditResultPostJSON;
import jakarta.websocket.Session;
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
            postOps.editPost(post);
            PostJSON postEntity = postOps.getPostInfo(post);
            if(postEntity!= null){
                Set<PostJSON> resReposts = successEdits.getPosts();
                resReposts.add(postEntity);
                successEdits.setPosts(resReposts);
            }
            else{
                Set<PostJSON> resReposts = failureEdits.getPosts();
                resReposts.add(post);
                failureEdits.setPosts(resReposts);
            }
        }
        session.getAsyncRemote().sendObject(successEdits);
        session.getAsyncRemote().sendObject(failureEdits);
        //send updated post to user who initiated the edit operation        
   }
   
}
