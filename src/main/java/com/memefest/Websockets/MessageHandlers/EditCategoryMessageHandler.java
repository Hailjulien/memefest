package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.Services.CategoryOperations;
import com.memefest.Websockets.JSON.EditCategoryJSON;
import com.memefest.Websockets.JSON.EditResultCategoryJSON;

import jakarta.websocket.Session;
import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
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
    public  void onMessage(EditCategoryJSON catEdit){
        Set<CategoryJSON> categories = catEdit.getCategories();
        
        EditResultCategoryJSON successEdits = new EditResultCategoryJSON(null, 200, "Success");
        EditResultCategoryJSON failureEdits = new EditResultCategoryJSON(null, 203, "Could not edit");

        for(CategoryJSON category : categories){
            try{
                catOps.editCategory((CategoryJSON)category);
                try{
                    CategoryJSON catEntity = catOps.getCategoryInfo((CategoryJSON)category);
                    Set<CategoryJSON> resCats = successEdits.getCategories();
                    if(resCats == null)
                        resCats = new HashSet<CategoryJSON>();
                    resCats.add(catEntity);
                    successEdits.setCategories(resCats);
                } 
                catch (NoResultException e) {
                        Set<CategoryJSON> resCats = failureEdits.getCategories();
                        if(resCats == null)
                            resCats = new HashSet<CategoryJSON>();
                        resCats.add(category);
                        failureEdits.setCategories(resCats);
                }
                catch (RollbackException ex) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<CategoryJSON> resCats = failureEdits.getCategories();
                    if(resCats == null)
                        resCats = new HashSet<CategoryJSON>();
                    resCats.add(category);
                    failureEdits.setCategories(resCats);
                }
            }
            catch(EJBException ex){
                failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                Set<CategoryJSON> resCats = failureEdits.getCategories();
                if(resCats == null)
                    resCats = new HashSet<CategoryJSON>();
                resCats.add(category);
                failureEdits.setCategories(resCats);
            }
                    
        }
        if(successEdits.getCategories() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getCategories() != null)
            session.getAsyncRemote().sendObject(failureEdits);
               
   }
   
}