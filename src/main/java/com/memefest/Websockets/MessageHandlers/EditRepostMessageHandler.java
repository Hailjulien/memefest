package com.memefest.Websockets.MessageHandlers;
import java.util.Set;
import com.memefest.DataAccess.JSON.RepostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.EditRepostJSON;
import com.memefest.Websockets.JSON.EditResultRepostJSON;

import jakarta.websocket.Session;
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
        Set<RepostJSON> posts = repostEdit.getPosts();
        EditResultRepostJSON successEdits = new EditResultRepostJSON(null, "success" ,200);
        EditResultRepostJSON failureEdits = new EditResultRepostJSON(null, "Could not edit" ,203);

           for(RepostJSON post : posts){
            postOps.editRepost(post);
            RepostJSON postEntity = postOps.getRepostInfo(post);
            if(postEntity!= null){
                Set<RepostJSON> resReposts = successEdits.getReposts();
                resReposts.add(postEntity);
                successEdits.setReposts(resReposts);
            }
            else{
                Set<RepostJSON> resReposts = failureEdits.getReposts();
                resReposts.add(post);
                failureEdits.setReposts(resReposts);
            }
        }
        session.getAsyncRemote().sendObject(successEdits);
        session.getAsyncRemote().sendObject(failureEdits);
        //send updated post to user who initiated the edit operation        
   }
}

