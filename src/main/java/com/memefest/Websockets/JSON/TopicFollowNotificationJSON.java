package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.TopicJSON;

@JsonRootName("TopicFollowNotification")
public class TopicFollowNotificationJSON extends FollowNotificationJSON{
    
    @JsonProperty("Topic")
    private TopicJSON topic;

    @JsonCreator
    public TopicFollowNotificationJSON(@JsonProperty("NotificationID") int notId,
                                            @JsonProperty("Topic") TopicJSON topic,
                                                @JsonProperty("Timestamp") LocalDateTime timestamp, 
                                                    @JsonProperty("Followers") Set<String> usernames) {
        super(notId,timestamp,usernames);
        this.topic = topic;         
    }
    
    public TopicJSON getTopic() {
        return topic;
    }

    public void setTopic(TopicJSON topic) {
        this.topic = topic;
    }
}
