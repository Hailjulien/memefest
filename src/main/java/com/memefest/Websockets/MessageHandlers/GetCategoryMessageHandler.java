package com.memefest.Websockets.MessageHandlers;

import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.Services.CategoryOperations;
import com.memefest.Websockets.JSON.GetCategoryJSON;
import com.memefest.Websockets.JSON.GetCategoryResultJSON;

import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetCategoryMessageHandler  implements MessageHandler.Whole<GetCategoryJSON>{

    private CategoryOperations catOps;
    private Session session;
 
    public GetCategoryMessageHandler(CategoryOperations catOps, Session session){
        this.catOps = catOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetCategoryJSON getCat){
        GetCategoryResultJSON notFound = new GetCategoryResultJSON( 203, "Not found categories", null);
        GetCategoryResultJSON found = new GetCategoryResultJSON(200, "Found categories", null);
        for(CategoryJSON category : getCat.getCategories()){
            category = catOps.getCategoryInfo(category);
            if(category!= null){
                found.getCategories().add(category);
            }
            else{
                notFound.getCategories().add(category);
            }
        }
        session.getAsyncRemote().sendObject(found);
        session.getAsyncRemote().sendObject(notFound);
    }
}