package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.Services.NotificationOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.EditEventPostJSON;
import com.memefest.Websockets.JSON.EditEventPostNotificationJSON;
import com.memefest.Websockets.JSON.EditFollowNotificationJSON;
import com.memefest.Websockets.JSON.EditPostJSON;
import com.memefest.Websockets.JSON.EditResultEventPostNotificationJSON;
import com.memefest.Websockets.JSON.EditResultFollowNotificationJSON;
import com.memefest.Websockets.JSON.EditResultPostJSON;
import com.memefest.Websockets.JSON.EventPostNotificationJSON;
import com.memefest.Websockets.JSON.FollowNotificationJSON;

import jakarta.websocket.Session;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;

public class EditFollowNotificationMessageHandler implements MessageHandler.Whole<EditFollowNotificationJSON>{

   private NotificationOperations notOps;
   private Session session;

   public EditFollowNotificationMessageHandler(NotificationOperations notOps, Session session){
       this.notOps = notOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
    @Override
   public  void onMessage(EditFollowNotificationJSON postEdit){
        Set<FollowNotificationJSON> posts = postEdit.getFollowNotifications();
        EditResultFollowNotificationJSON successEdits = new EditResultEventPostNotificationJSON(200, "Success", null);
        EditResultFollowNotificationJSON failureEdits = new EditResultEventPostNotificationJSON( 203, "Could not edit", null);

           for(EventPostNotificationJSON post : posts){
                try{
                    notOps.editEventPostNotification(post);
                    Set<EventPostNotificationJSON> resReposts = successEdits.getEventPostNotifications();
                    resReposts.add(post);
                    successEdits.setEventPostNotifications(resReposts);
                }
                catch(NoResultException ex){
                    if(post.isCanceled()){
                        Set<EventPostNotificationJSON> resReposts = successEdits.getEventPostNotifications();
                        resReposts.add(post);
                        successEdits.setEventPostNotifications(resReposts);
                    }
                    else{
                        Set<EventPostNotificationJSON> resReposts = failureEdits.getEventPostNotifications();
                        resReposts.add(post);
                        failureEdits.setEventPostNotifications(resReposts);
                    }
                }
        }
        session.getAsyncRemote().sendObject(successEdits);
        session.getAsyncRemote().sendObject(failureEdits);
        //send updated post to user who initiated the edit operation        
   }
   
}