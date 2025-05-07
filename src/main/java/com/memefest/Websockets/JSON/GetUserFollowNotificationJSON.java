package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetUSerFollowNotifications")
public class GetUserFollowNotificationJSON extends GetFollowNotificationJSON{
    
    @JsonProperty("UserFollowNotifications")
    private Set<UserFollowNotificationJSON> userFollowNots;

    @JsonCreator
    public GetUserFollowNotificationJSON(@JsonProperty("UserFollowNotifications") Set<UserFollowNotificationJSON> userFollowNotifications){
        super(userFollowNotifications.stream().map(candidate ->{
                return (FollowNotificationJSON)candidate;
            }   
        ).collect(Collectors.toSet()));
    }

    public void setUserFollowNotifications(Set<UserFollowNotificationJSON> userFollowNotifications){
        super.setFollowNotifications(userFollowNotifications.stream().map(candidate ->{
                return (FollowNotificationJSON)candidate;
            }   
        ).collect(Collectors.toSet()));
    }

    public Set<UserFollowNotificationJSON> getUserFollowNotifications(){
        return this.userFollowNots;
    }
}
