package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditFollowNorifications")
public class EditFollowNotificationJSON extends EditJSON{

    @JsonProperty("FollowNotifications")
    private Set<FollowNotificationJSON> followNots;

    @JsonCreator
    public EditFollowNotificationJSON(@JsonProperty("FollowNotifications") Set<FollowNotificationJSON> followNotifications){
        super(Editable.NOTIFICATION);
        this.followNots = followNotifications;
    }

    public void setFollowNotifications(Set<FollowNotificationJSON> followNotifications){
        this.followNots = followNotifications;
    }

    public Set<FollowNotificationJSON> getFollowNotifications(){
        return this.followNots;
    }
}
