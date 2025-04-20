package com.memefest.Websockets.MessageHandlers;
import java.util.Set;

import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Websockets.JSON.SearchEventJSON;
import com.memefest.Websockets.JSON.SearchResultEventJSON;
import jakarta.websocket.Session;
import jakarta.websocket.MessageHandler;

public class SearchEventMessageHandler implements MessageHandler.Whole<SearchEventJSON>{

   private EventOperations eventOps;
   private Session session;

   public SearchEventMessageHandler(EventOperations eventOps, Session session){
       this.eventOps = eventOps;
       this.session = session;
   }

   //add customisation filter according to users tastes here
@Override
   public  void onMessage(SearchEventJSON searchCommand){
        Set<EventJSON> events = eventOps.searchEvents(searchCommand.getEvent());
        //add customisation filter according to users tastes here
        SearchResultEventJSON result = new SearchResultEventJSON(null, 0, null);
        if(events.size() == 0){
            result.setResultId(204);
            result.setResultMessage("No results");
        }
        else{
            result.setResultId(200);
            result.setResultMessage("Success");
            result.setEvents(events);
        }
        session.getAsyncRemote().sendObject(result);
   }
}
