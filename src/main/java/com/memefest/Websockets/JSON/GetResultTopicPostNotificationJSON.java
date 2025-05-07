package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetResultTopicPostNotification")
public class GetResultTopicPostNotificationJSON extends GetResultPostNotificationJSON {
    
    @JsonProperty("TopicPostNotifications")
    private Set<TopicPostNotificationJSON> topicPostNotifications;

    @JsonCreator
    public GetResultTopicPostNotificationJSON(@JsonProperty("ResultCode") int resultCode,
                                    @JsonProperty("ResultMessage") String resultMessage,
                                    @JsonProperty("TopicPostNotifications") Set<TopicPostNotificationJSON> topicPostNotifications){
        super(resultCode, resultMessage, topicPostNotifications.stream().map(candidate ->{
            return (PostNotificationJSON) candidate;
        }).collect(Collectors.toSet()));
    }

    public void setTopicPostNotifications(Set<TopicPostNotificationJSON> topicPostNotifications){
        this.topicPostNotifications = topicPostNotifications;
        super.setPostNotifications(topicPostNotifications.stream().map(candidate -> {
            return (PostNotificationJSON) candidate;
        }).collect(Collectors.toSet()));
    }

    public Set<TopicPostNotificationJSON> getTopicPostNotifications(){
        return this.topicPostNotifications;
    }                               
}
