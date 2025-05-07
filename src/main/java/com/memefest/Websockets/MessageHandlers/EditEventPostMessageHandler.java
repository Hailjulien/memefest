package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.EditEventPostJSON;
import com.memefest.Websockets.JSON.EditResultEventPostJSON;
import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;

public class EditEventPostMessageHandler implements MessageHandler.Whole<EditEventPostJSON>{

   private PostOperations postOps;
   private Session session;

   public EditEventPostMessageHandler(PostOperations postOps, Session session){
       this.postOps = postOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
      @Override
   public  void onMessage(EditEventPostJSON eventEdit){
        Set<EventPostJSON> eventPosts = eventEdit.getEventPosts();
        EditResultEventPostJSON successEdits = new EditResultEventPostJSON(200, "Success", null);
        EditResultEventPostJSON failureEdits = new EditResultEventPostJSON(203, "Could not edit", null);
        for(EventPostJSON eventPost : eventPosts){
            try{
                try{
                    postOps.editEventPost((EventPostJSON)eventPost);
                    EventPostJSON eventPostEntity = postOps.getEventPostInfo(eventPost);
                    Set<EventPostJSON> resCats = successEdits.getEventPosts();
                    if(resCats == null)
                        resCats = new HashSet<EventPostJSON>();
                    resCats.add(eventPostEntity);
                    successEdits.setEventPosts(resCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<EventPostJSON> resCats = failureEdits.getEventPosts();
                    if(resCats == null)
                        resCats = new HashSet<EventPostJSON>();
                    resCats.add(eventPost);
                    failureEdits.setEventPosts(resCats);
                }
                catch (RollbackException ex) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<EventPostJSON> resCats = failureEdits.getEventPosts();
                    if(resCats == null)
                        resCats = new HashSet<EventPostJSON>();
                    resCats.add(eventPost);
                    failureEdits.setEventPosts(resCats);
                }
            }
            catch(EJBException ex){                    
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<EventPostJSON> resCats = failureEdits.getEventPosts();
                if(resCats == null)
                    resCats = new HashSet<EventPostJSON>();
                resCats.add(eventPost);
                failureEdits.setEventPosts(resCats);
            }
                    
        }
        if(successEdits.getEventPosts() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getEventPosts() != null)
            session.getAsyncRemote().sendObject(failureEdits);           
   }
}
