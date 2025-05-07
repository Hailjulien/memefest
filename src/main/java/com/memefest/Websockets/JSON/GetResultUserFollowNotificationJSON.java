package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetResultsUserFollowNotifications")
public class GetResultUserFollowNotificationJSON extends GetResultFollowNotificationJSON{
    
    @JsonProperty("UserFollowNotifications")
    private Set<UserFollowNotificationJSON> userFollowNotifications;

    @JsonCreator
    public GetResultUserFollowNotificationJSON(@JsonProperty("ResultCode") int resultCode,
                                @JsonProperty("ResultMessage") String resultMessage,
                                @JsonProperty("UserFollowNotifications") Set<UserFollowNotificationJSON> userFollowNotifications){
        super(resultCode, resultMessage, userFollowNotifications.stream().map(candidate ->{
                return (FollowNotificationJSON)candidate;
            }
        ).collect(Collectors.toSet()));
        this.userFollowNotifications = userFollowNotifications;
    }

    public void setUserFollowNotifications(Set<UserFollowNotificationJSON> userFollowNotifications){
        super.setFollowNotifications(userFollowNotifications.stream().map(candidate ->{
                return (FollowNotificationJSON)candidate;
            }
        ).collect(Collectors.toSet()));
        this.userFollowNotifications = userFollowNotifications;
    }

    public Set<UserFollowNotificationJSON> getUserFollowNotifications(){
        return this.userFollowNotifications;
    }
}
