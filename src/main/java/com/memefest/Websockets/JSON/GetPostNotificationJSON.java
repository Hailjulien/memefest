package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetPostNotification")
public class GetPostNotificationJSON extends GetNotificationJSON {
    
    @JsonProperty("PostNotifications")
    private Set<PostNotificationJSON> postNotifications;

    @JsonCreator
    public GetPostNotificationJSON(@JsonProperty("PostNotifications") Set<PostNotificationJSON> postNotifications){
        super(postNotifications.stream().map(candidate ->{
            return (NotificationJSON)candidate;
        }).collect(Collectors.toSet()));
    }

    public void setPostNotifications(Set<PostNotificationJSON> postNotifications){
        this.postNotifications= postNotifications;
        super.setNotifications(postNotifications.stream().map(candidate ->{
            return (NotificationJSON)candidate;
        }).collect(Collectors.toSet()));
    }

    public Set<PostNotificationJSON> getPostNotifications(){
        return postNotifications;
    }
}
