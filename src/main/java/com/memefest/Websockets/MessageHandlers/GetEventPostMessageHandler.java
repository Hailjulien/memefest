package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.GetEventPostJSON;
import com.memefest.Websockets.JSON.GetEventPostResultJSON;

import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetEventPostMessageHandler implements MessageHandler.Whole<GetEventPostJSON>{

    private PostOperations postOps;
    private EventOperations eventOps;
    private Session session;
 
    public GetEventPostMessageHandler(PostOperations postOps, Session session){
        this.postOps = postOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetEventPostJSON getEventPost){
        GetEventPostResultJSON notFound = new GetEventPostResultJSON( 203, "Not found Event Posts", null);
        GetEventPostResultJSON found = new GetEventPostResultJSON(200, "Found Event Posts", null);
        for(EventPostJSON eventPost : getEventPost.getEventPosts()){
            EventJSON event = eventOps.getEventInfo(eventPost.getEvent());
            PostJSON post = postOps.getPostInfo(new PostJSON(eventPost.getPostId(), null, null, 0, 0, null));
            eventPost.setComment(post.getComment());
            eventPost.setCreated(post.getCreated());
            eventPost.setUpvotes(post.getUpvotes());
            eventPost.setDownvotes(post.getDownvotes());
            eventPost.setPostId(post.getPostId());
            eventPost.setEvent(event);
            if(event != null && post != null){
                Set<EventPostJSON> events = found.getEventPosts();
                events.add(eventPost);
                found.setEventPosts(events);
            }
            else{
                Set<EventPostJSON> events = notFound.getEventPosts();
                events.add(eventPost);
                notFound.setEventPosts(events);
            }
        }
        session.getAsyncRemote().sendObject(found);
        session.getAsyncRemote().sendObject(notFound);
    }   
}
