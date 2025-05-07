package com.memefest.Websockets.JSON;


import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetTopicPostNotification")
public class GetTopicPostNotificationJSON extends GetPostNotificationJSON{

    @JsonProperty("TopicPostNotifications")
    public Set<TopicPostNotificationJSON> topicPostNotifications;

    @JsonCreator
    public GetTopicPostNotificationJSON(@JsonProperty("TopicPostNotifications") Set<TopicPostNotificationJSON> topicPostNotifications){
        super(topicPostNotifications.stream().map(candidate ->{
            return (PostNotificationJSON)candidate;
        }).collect(Collectors.toSet()));
        this.topicPostNotifications = topicPostNotifications;
    }

    public void setTopicPostNotifications(Set<TopicPostNotificationJSON> topicPostNotifications){
        this.topicPostNotifications = topicPostNotifications;
        super.setPostNotifications(topicPostNotifications.stream().map(candidate ->{
            return (PostNotificationJSON)candidate;
        }).collect(Collectors.toSet()));
    }

    public Set<TopicPostNotificationJSON> getTopicPostNotifications(){
        return this.topicPostNotifications;
    }
    
}
