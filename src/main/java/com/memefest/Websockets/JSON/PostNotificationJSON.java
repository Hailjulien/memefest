package com.memefest.Websockets.JSON;

import com.memefest.DataAccess.JSON.PostJSON;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("PostNotification")
public class PostNotificationJSON extends NotificationJSON{
  
    @JsonProperty("Post")
    private PostJSON post;

    @JsonCreator
    public PostNotificationJSON(@JsonProperty("NotificationID") int notId,
                                    @JsonProperty("Post") PostJSON postJSON,
                                         @JsonProperty("TimeStamp") LocalDateTime time){
        super(notId, time, Notification.POST);
        this.post = postJSON;
    }
    public void setPost(PostJSON post){
        this.post = post;
    }

    public PostJSON getPost(){
        return this.post;
    }
}