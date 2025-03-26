package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.Services.CategoryOperations;
import com.memefest.Services.Impl.FeedsEndPointService;
import com.memefest.Websockets.JSON.EditPostJSON;
import com.memefest.Websockets.JSON.SearchCategoryJSON;
import com.memefest.Websockets.JSON.SearchEventJSON;
import com.memefest.Websockets.JSON.SearchResultCategoryJSON;
import com.memefest.Websockets.JSON.SearchResultEventJSON;

import jakarta.websocket.Session;
import jakarta.websocket.MessageHandler;

public class SearchCategoryMessageHandler implements MessageHandler.Whole<SearchCategoryJSON>{

   private CategoryOperations catOps;
   private Session session;

   public SearchCategoryMessageHandler(CategoryOperations catOps, Session session){
       this.catOps = catOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
   @Override
   public  void onMessage(SearchCategoryJSON searchCommand){
        Set<CategoryJSON> categories = catOps.searchCategory(searchCommand.getCategory());
        //add customisation filter according to users tastes here
        SearchResultCategoryJSON result = new SearchResultCategoryJSON(null, 0, null);
        if(categories.size() == 0){
            result.setResultId(204);
            result.setResultMessage("No results");
        }
        else{
            result.setResultId(200);
            result.setResultMessage("Success");
            result.setCategories(categories);
        }
        session.getAsyncRemote().sendObject(result);
   }
}

