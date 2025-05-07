package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditResultFollowNotification")
public class EditResultFollowNotificationJSON extends EditResultJSON{
    
    @JsonProperty("FollowNotifications")
    private Set<FollowNotificationJSON> followNots;

    @JsonCreator
    public EditResultFollowNotificationJSON(@JsonProperty("ResultCode") int resultCode,
                                        @JsonProperty("ResultMessage") String resultMessage,
                                            @JsonProperty("FollowNotifications") Set<FollowNotificationJSON> followNotifications){
        super(Editable.NOTIFICATION, resultCode, resultMessage);
        this.followNots = followNotifications;
    }

    public Set<FollowNotificationJSON> getFollowNotifications(){
        return this.followNots;
    }

    public void setFollowNotifications(Set<FollowNotificationJSON> followNotifications){
        this.followNots = followNotifications;
    }
}
