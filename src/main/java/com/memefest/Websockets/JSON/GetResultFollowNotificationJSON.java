package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetResultFollowNotification")
public class GetResultFollowNotificationJSON extends GetResultNotificationJSON{
    
    @JsonProperty("FollowNotifications")
    private Set<FollowNotificationJSON> followNotifications;

    @JsonCreator
    public GetResultFollowNotificationJSON( @JsonProperty("ResultCode") int resultCode,
                                        @JsonProperty("ResultMessage") String resultMessage,
                            @JsonProperty("FollowNotifications") Set<FollowNotificationJSON> followNotifications){
        super(resultCode, resultMessage, followNotifications.stream().map(candidate -> {
            return (NotificationJSON) candidate;
        }).collect(Collectors.toSet()));
    }

    public void setFollowNotifications(Set<FollowNotificationJSON> followNotifications){
        this.followNotifications = followNotifications;
        super.setNotifications(followNotifications.stream().map(candidaate ->{
            return (NotificationJSON) candidaate;
        }).collect(Collectors.toSet()));
    }

    public Set<FollowNotificationJSON> getFollowNotifications(){
        return followNotifications;
    }
}
