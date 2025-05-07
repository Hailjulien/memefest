package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.UserJSON;

@JsonRootName("EditTopicFollowNotifications")
public class EditTopicFollowNotificationJSON extends FollowNotificationJSON{

    @JsonProperty("TopicFollowNotifications")
    private Set<TopicFollowNotificationJSON> followNots;

    @JsonCreator
    public EditTopicFollowNotificationJSON(@JsonProperty("NotificationId") int notId,
                            @JsonProperty("Timestamp") LocalDateTime dateTime,
                            @JsonProperty("User") UserJSON user,
                            @JsonProperty("TopicFollowNotifications") Set<TopicFollowNotificationJSON> topicFollowNotifications){
        super(notId,dateTime,user);
        this.followNots = topicFollowNotifications;
    }

    public void setTopicFollowNotifications(Set<TopicFollowNotificationJSON> followNotifications){
        this.followNots = followNotifications;
    }

    public Set<TopicFollowNotificationJSON> getTopicFollowNotifications(){
        return this.followNots;
    }
}