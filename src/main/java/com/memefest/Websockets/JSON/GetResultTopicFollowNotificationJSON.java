package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetResultsTopicFollowNotifications")
public class GetResultTopicFollowNotificationJSON extends GetResultFollowNotificationJSON{
    
    @JsonProperty("UserFollowNotifications")
    private Set<TopicFollowNotificationJSON> topicFollowNotifications;

    @JsonCreator
    public GetResultTopicFollowNotificationJSON(@JsonProperty("ResultCode") int resultCode,
                                @JsonProperty("ResultMessage") String resultMessage,
                                @JsonProperty("TopicFollowNotifications") Set<TopicFollowNotificationJSON> topicFollowNotifications){
        super(resultCode, resultMessage, topicFollowNotifications.stream().map(candidate ->{
                return (FollowNotificationJSON)candidate;
            }
        ).collect(Collectors.toSet()));
        this.topicFollowNotifications = topicFollowNotifications;
    }

    public void setTopicFollowNotifications(Set<TopicFollowNotificationJSON> topicFollowNotifications){
        super.setFollowNotifications(topicFollowNotifications.stream().map(candidate ->{
                return (FollowNotificationJSON)candidate;
            }
        ).collect(Collectors.toSet()));
        this.topicFollowNotifications = topicFollowNotifications;
    }

    public Set<TopicFollowNotificationJSON> getTopicFollowNotifications(){
        return this.topicFollowNotifications;
    }
}
