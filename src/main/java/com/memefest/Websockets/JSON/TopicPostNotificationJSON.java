package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;

@JsonRootName("TopicPostNotification")
public class TopicPostNotificationJSON extends PostNotificationJSON{
    
    @JsonProperty("TopicPost")
    private TopicPostJSON topicPost;

    @JsonCreator
    public TopicPostNotificationJSON(@JsonProperty("NotificationID") int notId, 
                                        @JsonProperty("TopicPost") TopicPostJSON topicPost,
                                            @JsonProperty("TimeStamp") LocalDateTime time){
        super(notId, (PostJSON)topicPost,time);
        this.topicPost = topicPost;
    }

    public TopicPostJSON getTopicPost() {
        return topicPost;
    }

    public void setTopicPost(TopicPostJSON topicPost) {
        this.topicPost = topicPost;
    }
}
