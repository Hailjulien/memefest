package com.memefest.Websockets.MessageHandlers;
import java.util.HashSet;
import java.util.Set;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.UserOperations;
import com.memefest.Websockets.JSON.EditResultUserJSON;
import com.memefest.Websockets.JSON.EditUserJSON;
import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.websocket.MessageHandler;

public class EditUserMessageHandler implements MessageHandler.Whole<EditUserJSON>{

   private UserOperations userOps;
   private Session session;

   public EditUserMessageHandler(UserOperations userOps, Session session){
       this.userOps = userOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
    @Override
    public  void onMessage(EditUserJSON userEdit){
        Set<UserJSON> users = userEdit.getUsers();
        EditResultUserJSON successEdits = new EditResultUserJSON(200, "Success",null);
        EditResultUserJSON failureEdits = new EditResultUserJSON(203, "Could not edit", null);

        for(UserJSON user : users){
            try{
                userOps.editUser((UserJSON)user);
                try{
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
                catch (RollbackException ex) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<UserJSON> resUsers = failureEdits.getUsers();
                    if(resUsers == null)
                        resUsers = new HashSet<UserJSON>();
                    resUsers.add(user);
                    failureEdits.setUsers(resUsers);
                }
            }
            catch(EJBException ex){
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<UserJSON> resUsers = failureEdits.getUsers();
                if(resUsers == null)
                    resUsers = new HashSet<UserJSON>();
                resUsers.add(user);
                failureEdits.setUsers(resUsers);
            }
                    
        }
        if(successEdits.getUsers() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getUsers() != null)
            session.getAsyncRemote().sendObject(failureEdits);
            //send updated post to user who initiated the edit operation        
   }
   
}