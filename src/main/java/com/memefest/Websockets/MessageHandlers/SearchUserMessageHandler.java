package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.UserOperations;
import com.memefest.Websockets.JSON.SearchResultUserJSON;
import com.memefest.Websockets.JSON.SearchUserJSON;

import jakarta.websocket.Session;
import jakarta.websocket.MessageHandler;

public class SearchUserMessageHandler implements MessageHandler.Whole<SearchUserJSON>{

   private UserOperations userOps;
   private Session session;

   public SearchUserMessageHandler(UserOperations userOps, Session session){
       this.userOps = userOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
   @Override
   public  void onMessage(SearchUserJSON searchCommand){
        Set<UserJSON> users = userOps.searchByUsername(searchCommand.getUser());
        //add customisation filter according to users tastes here
        SearchResultUserJSON result = new SearchResultUserJSON(null, 0, null);
        if(users.size() == 0){
            result.setResultId(204);
            result.setResultMessage("No results");
        }
        else{
            result.setResultId(200);
            result.setResultMessage("Success");
            result.setUsers(users);
        }
        session.getAsyncRemote().sendObject(result);
   }  
}