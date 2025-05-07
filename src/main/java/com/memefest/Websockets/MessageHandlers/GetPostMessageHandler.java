package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Websockets.JSON.GetPostJSON;
import com.memefest.Websockets.JSON.GetPostResultJSON;

import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetPostMessageHandler implements MessageHandler.Whole<GetPostJSON>{

    private PostOperations postOps;
    private Session session;
 
    public GetPostMessageHandler(PostOperations postOps, Session session){
        this.postOps = postOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetPostJSON getPost){
        GetPostResultJSON notFound = new GetPostResultJSON( 203, "Not found Event Posts", null);
        GetPostResultJSON found = new GetPostResultJSON(200, "Found Event Posts", null);
        for(PostJSON post : getPost.getPosts()){
            post = postOps.getPostInfo(post);
            if(post != null){
                Set<PostJSON> posts = found.getPostResult();
                posts.add(post);
                found.setPostResult(posts);
            }
            else{
                Set<PostJSON> posts = notFound.getPostResult();
                posts.add(post);
                notFound.setPostResult(posts);
            }
        }
        session.getAsyncRemote().sendObject(found);
        session.getAsyncRemote().sendObject(notFound);
    }   
}
