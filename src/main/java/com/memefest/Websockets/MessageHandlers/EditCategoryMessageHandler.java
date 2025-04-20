package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.Services.CategoryOperations;
import com.memefest.Websockets.JSON.EditCategoryJSON;
import com.memefest.Websockets.JSON.EditResultCategoryJSON;

import jakarta.websocket.Session;
import jakarta.websocket.MessageHandler;

public class EditCategoryMessageHandler implements MessageHandler.Whole<EditCategoryJSON>{

   private CategoryOperations catOps;
   private Session session;

   public EditCategoryMessageHandler(CategoryOperations catOps, Session session){
       this.catOps = catOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
    @Override
    public  void onMessage(EditCategoryJSON eventEdit){
        Set<CategoryJSON> categories = eventEdit.getCategories();
        EditResultCategoryJSON successEdits = new EditResultCategoryJSON(null, 200, "Success");
        EditResultCategoryJSON failureEdits = new EditResultCategoryJSON(null, 203, "Could not edit");

           for(CategoryJSON category : categories){
            catOps.editCategory(category);
            CategoryJSON catEntity = catOps.getCategoryInfo(category);
            if(catEntity!= null){
                Set<CategoryJSON> resCats = successEdits.getCategories();
                resCats.add(catEntity);
                successEdits.setCategories(categories);
            }
            else{
                Set<CategoryJSON> resCats = failureEdits.getCategories();
                resCats.add(category);
                failureEdits.setCategories(resCats);
            }
        }
        session.getAsyncRemote().sendObject(successEdits);
        session.getAsyncRemote().sendObject(failureEdits);
        //send updated post to user who initiated the edit operation        
   }
   
}