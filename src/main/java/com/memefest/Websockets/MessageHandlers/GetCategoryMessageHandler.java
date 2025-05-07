package com.memefest.Websockets.MessageHandlers;

import java.util.HashSet;
import java.util.Set;

import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.Services.CategoryOperations;
import com.memefest.Websockets.JSON.GetCategoryJSON;
import com.memefest.Websockets.JSON.GetResultCategoryJSON;

import jakarta.ejb.EJBException;
import jakarta.persistence.NoResultException;
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
        GetResultCategoryJSON successEdits = new GetResultCategoryJSON(200, "Success",null);
        GetResultCategoryJSON failureEdits = new GetResultCategoryJSON(203, "Could not edit", null);

           for(CategoryJSON categ : getCat.getCategories()){
                try{
                    CategoryJSON catEntity = catOps.getCategoryInfo(categ);
                    if(catEntity!= null){
                        Set<CategoryJSON> resCats = successEdits.getCategories();
                        resCats.add(catEntity);
                        successEdits.setCategories(resCats);
                    }
                }      
                catch(EJBException ex){
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + ex.getMessage());
                    Set<CategoryJSON> resCats = failureEdits.getCategories();
                    if(resCats == null)
                        resCats = new HashSet<CategoryJSON>();
                    resCats.add(categ);
                    failureEdits.setCategories(resCats);
                } 
                catch (NoResultException e) {
                    failureEdits.setResultMessage(failureEdits.getResultMessage() + "," + e.getMessage());
                    Set<CategoryJSON> resCats = failureEdits.getCategories();
                    if(resCats == null)
                        resCats = new HashSet<CategoryJSON>();
                    resCats.add(categ);
                    failureEdits.setCategories(resCats);
                }
        }
        if(successEdits.getCategories() != null)
            session.getAsyncRemote().sendObject(successEdits);
        if(failureEdits.getCategories() != null)
            session.getAsyncRemote().sendObject(failureEdits);
        //send updated post to user who 
    }
}