package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.Services.NotificationOperations;
import com.memefest.Websockets.JSON.EditResultUserFollowNotificationJSON;
import com.memefest.Websockets.JSON.EditUserFollowNotificationJSON;
import com.memefest.Websockets.JSON.UserFollowNotificationJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;

public class EditUserFollowNotificationMessageHandler implements MessageHandler.Whole<EditUserFollowNotificationJSON>{

   private NotificationOperations notOps;
   private Session session;

   public EditUserFollowNotificationMessageHandler(NotificationOperations notOps, Session session){
       this.notOps = notOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
    @Override
   public  void onMessage(EditUserFollowNotificationJSON postEdit){
        Set<UserFollowNotificationJSON> followNots = postEdit.getUserFollowNotifications();
        EditResultUserFollowNotificationJSON successEdits = new EditResultUserFollowNotificationJSON( 200, "Success", null);
        EditResultUserFollowNotificationJSON failureEdits = new EditResultUserFollowNotificationJSON( 203, "Could not edit", null);

        for(UserFollowNotificationJSON userFollow : followNots){
            try{
                notOps.editUserFollowNotification(userFollow);
                Set<UserFollowNotificationJSON> topicFollowEntities = notOps.getUserFollowNotificationInfo(userFollow);
                if(topicFollowEntities.size() > 1)
                    throw new NoResultException("Multiple results");
                Set<UserFollowNotificationJSON> eventCats  = successEdits.getUserFollowNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<UserFollowNotificationJSON>();
                eventCats.addAll(topicFollowEntities);
                successEdits.setUserFollowNotifications(eventCats);
            }
            catch (NoResultException e) {
                if(userFollow.isCanceled()){
                        Set<UserFollowNotificationJSON> eventCats  = successEdits.getUserFollowNotifications();
                        if(eventCats == null)
                            eventCats = new HashSet<UserFollowNotificationJSON>();
                        eventCats.add(userFollow);
                        successEdits.setUserFollowNotifications(eventCats);
                }
                else{
                    successEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<UserFollowNotificationJSON> eventCats  = failureEdits.getUserFollowNotifications();
                    if(eventCats == null)
                        eventCats = new HashSet<UserFollowNotificationJSON>();
                        eventCats.add(userFollow);
                    successEdits.setUserFollowNotifications(eventCats);
                }
            }
            catch (RollbackException ex) {
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<UserFollowNotificationJSON> eventCats  = failureEdits.getUserFollowNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<UserFollowNotificationJSON>();
                eventCats.add(userFollow);
                failureEdits.setUserFollowNotifications(eventCats);
            }
            catch(EJBException ex){
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<UserFollowNotificationJSON> eventCats  = failureEdits.getUserFollowNotifications();
                if(eventCats == null)
                    eventCats = new HashSet<UserFollowNotificationJSON>();
                eventCats.add(userFollow);
                failureEdits.setUserFollowNotifications(eventCats);
            }
                    
        }
        if(successEdits.getUserFollowNotifications() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getUserFollowNotifications() != null)
            session.getAsyncRemote().sendObject(failureEdits);                
   }
   
}
