package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditUserFollowNorifications")
public class EditUserFollowNotificationJSON extends EditFollowNotificationJSON{

    @JsonProperty("UserFollowNotifications")
    private Set<UserFollowNotificationJSON> followNots;

    @JsonCreator
    public EditUserFollowNotificationJSON(@JsonProperty("UserFollowNotifications") Set<UserFollowNotificationJSON> userFollowNotifications){
        super(userFollowNotifications.stream().map(candidate ->{
                return (FollowNotificationJSON)candidate;
            }
        ).collect(Collectors.toSet()));
        this.followNots = userFollowNotifications;
    }

    public void setUserFollowNotifications(Set<UserFollowNotificationJSON> followNotifications){
        super.setFollowNotifications(followNotifications.stream().map(candidate ->{
            return (FollowNotificationJSON)candidate;
        }
    ).collect(Collectors.toSet()));
        this.followNots = followNotifications;
    }

    public Set<UserFollowNotificationJSON> getUserFollowNotifications(){
        return this.followNots;
    }
}
