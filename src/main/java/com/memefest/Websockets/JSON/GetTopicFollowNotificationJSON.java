package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetTopicFollowNotification")
public class GetTopicFollowNotificationJSON extends GetFollowNotificationJSON{
    
    @JsonProperty("TopicFollowNotifications")
    private Set<TopicFollowNotificationJSON> topicFollowNots;

    public GetTopicFollowNotificationJSON(@JsonProperty("TopicFollowNotifications") Set<TopicFollowNotificationJSON> topicFollowNotifications){
        super(topicFollowNotifications.stream().map(candidate ->{
            return (FollowNotificationJSON)candidate;
        }).collect(Collectors.toSet()));
        this.topicFollowNots = topicFollowNotifications;
    }

    public void setTopicFollowNotifications(Set<TopicFollowNotificationJSON> topicFollowNotifications){
        super.setFollowNotifications(topicFollowNotifications.stream().map(candidate ->{
            return (FollowNotificationJSON)candidate;
        }).collect(Collectors.toSet()));
    }

    public Set<TopicFollowNotificationJSON> getTopicFollowNotifications(){
        return this.topicFollowNots;
    }

}
