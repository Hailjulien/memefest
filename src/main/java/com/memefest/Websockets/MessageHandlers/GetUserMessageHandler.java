package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.UserOperations;
import com.memefest.Websockets.JSON.EditResultAdminJSON;
import com.memefest.Websockets.JSON.GetUserJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.websocket.MessageHandler;

public class GetUserMessageHandler implements MessageHandler.Whole<GetUserJSON>{

   private UserOperations userOps;
   private Session session;

   public GetUserMessageHandler(UserOperations userOps, Session session){
       this.userOps = userOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetUserJSON userEdit){
        Set<UserJSON> users = userEdit.getUsers();
        EditResultAdminJSON successEdits = new EditResultAdminJSON(200, "Success",null);
        EditResultAdminJSON failureEdits = new EditResultAdminJSON(203, "Could not edit", null);

           for(UserJSON user : users){
                try{
                    UserJSON userEntity = userOps.getUserInfo(user);
                    if(userEntity!= null){
                        Set<UserJSON> resUsers = successEdits.getUsers();
                        resUsers.add(userEntity);
                        successEdits.setUsers(resUsers);
                    }
                }      
                catch(EJBException ex){
                    UserJSON userEntity = userOps.getUserInfo((UserJSON)user);
                    Set<UserJSON> resUsers = successEdits.getUsers();
                    if(resUsers == null)
                        resUsers = new HashSet<UserJSON>();
                    resUsers.add(userEntity);
                    successEdits.setUsers(resUsers);
                } 
                catch (NoResultException e) {
                    Set<UserJSON> resUsers = failureEdits.getUsers();
                    if(resUsers == null)
                        resUsers = new HashSet<UserJSON>();
                    resUsers.add(user);
                    failureEdits.setUsers(resUsers);
                }
        }
        if(successEdits.getAdmins() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getAdmins() != null)
        session.getAsyncRemote().sendObject(failureEdits);
        //send updated post to user who initiated the edit operation       
   }
   
}