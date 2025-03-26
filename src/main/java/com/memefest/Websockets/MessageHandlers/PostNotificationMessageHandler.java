package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Services.UserOperations;
import com.memefest.Services.Impl.FeedsEndPointService;
import com.memefest.Websockets.JSON.EditPostJSON;
import com.memefest.Websockets.JSON.PostNotificationJSON;

import jakarta.websocket.Session;
import jakarta.websocket.MessageHandler;

public class PostNotificationMessageHandler implements MessageHandler.Whole<PostNotificationJSON>{

   private PostOperations postOps;
   private UserOperations userOps;
   private Session session;

   public PostNotificationMessageHandler(PostOperations postOps, UserOperations userOps, Session session){
       this.postOps = postOps;
       this.userOps = userOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
   @Override
   public  void onMessage(PostNotificationJSON postNots){
        
   }
}

