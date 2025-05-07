package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.UserJSON;

@JsonRootName("UserFollowNotification")
public class UserFollowNotificationJSON extends FollowNotificationJSON{

    @JsonCreator
    public UserFollowNotificationJSON(@JsonProperty("NotificationID") int notID, 
                                        @JsonProperty("User") UserJSON user,
                                            @JsonProperty("TimeStamp") LocalDateTime time,
                                                @JsonProperty("Followers") Set<String> followers){
        super(notID, time, followers, user);
    }

}
