package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("FollowNotification")
public class FollowNotificationJSON extends NotificationJSON{
    
    @JsonProperty("Follows")
    private Set<String> usernames;

    @JsonCreator
    public FollowNotificationJSON(@JsonProperty("NotificationID") int notId,
                                    @JsonProperty("Timestamp") LocalDateTime timestamp, 
                                        Set<String> usernames) {
        super(notId, timestamp, Notification.FOLLOW);
        this.usernames = usernames;
    }

    public void setFollows(Set<String> usernames) {
        this.usernames = usernames;
    }

    public Set<String> getFollows(){
        return this.usernames;
    }
}
