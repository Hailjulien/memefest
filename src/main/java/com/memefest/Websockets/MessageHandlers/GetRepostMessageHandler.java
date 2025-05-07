package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.DataAccess.JSON.RepostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.GetRepostJSON;
import com.memefest.Websockets.JSON.GetRepostResultJSON;

import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetRepostMessageHandler implements MessageHandler.Whole<GetRepostJSON>{

    private PostOperations postOps;
    private Session session;
 
    public GetRepostMessageHandler(PostOperations postOps, Session session){
        this.postOps = postOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetRepostJSON getRepost){
        GetRepostResultJSON notFound = new GetRepostResultJSON( 203, "Not found Event Posts", null);
        GetRepostResultJSON found = new GetRepostResultJSON(200, "Found Event Posts", null);
        for(RepostJSON rePost : getRepost.getReposts()){
            rePost = postOps.getRepostInfo(rePost);
            if(rePost != null){
                Set<RepostJSON> reposts = found.getReposts();
                reposts.add(rePost);
                found.setReposts(reposts);
            }
            else{
                Set<RepostJSON> events = notFound.getReposts();
                events.add(rePost);
                notFound.setReposts(events);
            }
        }
        session.getAsyncRemote().sendObject(found);
        session.getAsyncRemote().sendObject(notFound);
    }   
}
