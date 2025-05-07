package com.memefest.Websockets.MessageHandlers;

import java.util.Set;

import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;
import com.memefest.Services.PostOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Websockets.JSON.GetTopicPostJSON;
import com.memefest.Websockets.JSON.GetTopicPostResultJSON;

import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;

public class GetTopicPostMessageHandler implements MessageHandler.Whole<GetTopicPostJSON>{

    private PostOperations postOps;
    private TopicOperations topicOps;
    private Session session;
 
    public GetTopicPostMessageHandler(TopicOperations topicOps, Session session){
        this.topicOps = topicOps;
        this.session = session;
    }
 
    //add customisation filter according to users tastes here
    @Override
    public  void onMessage(GetTopicPostJSON getTopicPost){
        GetTopicPostResultJSON notFound = new GetTopicPostResultJSON( 203, "Not found Event Posts", null);
        GetTopicPostResultJSON found = new GetTopicPostResultJSON(200, "Found Event Posts", null);
        for(TopicPostJSON topicPost : getTopicPost.getTopicPosts()){
            TopicJSON topic = topicOps.getTopicInfo(topicPost.getTopic());
            PostJSON post = postOps.getPostInfo(new PostJSON(topicPost.getPostId(), null, null, 0, 0, null));
            topicPost.setComment(post.getComment());
            topicPost.setCreated(post.getCreated());
            topicPost.setUpvotes(post.getUpvotes());
            topicPost.setDownvotes(post.getDownvotes());
            topicPost.setPostId(post.getPostId());
            topicPost.setTopic(topic);
            if(topic != null && post != null){
                Set<TopicPostJSON> topics = found.getTopicPosts();
                topics.add(topicPost);
                found.setTopicPosts(topics);
            }
            else{
                Set<TopicPostJSON> topics = notFound.getTopicPosts();
                topics.add(topicPost);
                notFound.setTopicPosts(topics);
            }
        }
        session.getAsyncRemote().sendObject(found);
        session.getAsyncRemote().sendObject(notFound);
    }   
}
