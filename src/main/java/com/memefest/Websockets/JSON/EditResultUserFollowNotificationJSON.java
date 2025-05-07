package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditResultUserFollowNotification")
public class EditResultUserFollowNotificationJSON extends EditResultFollowNotificationJSON{
    
    @JsonProperty("UserFollowNotifications")
    private Set<UserFollowNotificationJSON> followNots;

    @JsonCreator
    public EditResultUserFollowNotificationJSON(@JsonProperty("ResultCode") int resultCode,
                                        @JsonProperty("ResultMessage") String resultMessage,
                                            @JsonProperty("UserFollowNotifications") Set<UserFollowNotificationJSON> followNotifications){
        super(resultCode, resultMessage, followNotifications.stream().map(candidate ->{
            return (FollowNotificationJSON)candidate;
        }).collect(Collectors.toSet()));
        this.followNots = followNotifications;
    }

    public Set<UserFollowNotificationJSON> getUserFollowNotifications(){
        return this.followNots;
    }

    public void setUserFollowNotifications(Set<UserFollowNotificationJSON> followNotifications){
        super.setFollowNotifications(followNotifications.stream().map(candidate ->{
            return (FollowNotificationJSON)candidate;
        }).collect(Collectors.toSet()));
        this.followNots = followNotifications;
    }
}

