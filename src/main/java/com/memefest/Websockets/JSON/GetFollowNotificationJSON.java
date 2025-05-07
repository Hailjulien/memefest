package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetFollowNotification")
public class GetFollowNotificationJSON extends GetNotificationJSON {
    
    @JsonProperty("FollowNotifications")
    private Set<FollowNotificationJSON> followNotifications;

    @JsonCreator
    public GetFollowNotificationJSON(@JsonProperty("FollowNotifications") Set<FollowNotificationJSON> followNotifications){
        super(followNotifications.stream().map(candidate ->{
            return (NotificationJSON)candidate;
        }).collect(Collectors.toSet()));
        this.followNotifications = followNotifications;
    }

    public void setFollowNotifications(Set<FollowNotificationJSON> followNotifications){
        this.followNotifications = followNotifications;
        super.setNotifications(followNotifications.stream().map(candidate ->{
            return (NotificationJSON)candidate;
        }).collect(Collectors.toSet()));
    }

    public Set<FollowNotificationJSON> getFollowNotifications(){
        return this.followNotifications;
    }
}
