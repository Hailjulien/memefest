package com.memefest.Websockets.MessageHandlers;

import java.util.Set;
import com.memefest.DataAccess.JSON.AdminJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.UserOperations;
import com.memefest.Websockets.JSON.EditAdminJSON;
import com.memefest.Websockets.JSON.EditResultAdminJSON;

import jakarta.websocket.Session;
import jakarta.websocket.MessageHandler;

public class EditAdminMessageHandler implements MessageHandler.Whole<EditAdminJSON>{

   private UserOperations userOps;
   private Session session;

   public EditAdminMessageHandler(UserOperations userOps, Session session){
       this.userOps = userOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
    @Override
    public  void onMessage(EditAdminJSON adminEdit){
        Set<AdminJSON> admins = adminEdit.getAdmins();
        EditResultAdminJSON successEdits = new EditResultAdminJSON(200, "Success",null);
        EditResultAdminJSON failureEdits = new EditResultAdminJSON(203, "Could not edit", null);

           for(AdminJSON admin : admins){
            userOps.editUser(admin);
            UserJSON adminEntity = userOps.getUserInfo((UserJSON)admin);

            if(adminEntity!= null){
                admin.setCanceled(false);
                admin.setContacts(adminEntity.getContacts());
                admin.setEmail(adminEntity.getEmail());
                admin.setUserId(adminEntity.getUserId());
                admin.setUsername(adminEntity.getUsername());
                admin.setFirstName(adminEntity.getFirstName());
                admin.setLastName(adminEntity.getLastName());
                Set<UserJSON> resUsers = successEdits.getUsers();
                resUsers.add(adminEntity);
                successEdits.setUsers(resUsers);
            }
            else{
                Set<AdminJSON> resAdmins = failureEdits.getAdmins();
                resAdmins.add(admin);
                failureEdits.setAdmins(admins);
            }
        }
        session.getAsyncRemote().sendObject(successEdits);
        session.getAsyncRemote().sendObject(failureEdits);
        //send updated post to user who initiated the edit operation        
   }
   
}