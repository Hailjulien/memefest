package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditResultsTopicFollowNotification")
public class EditResultTopicFollowNotificationJSON extends EditResultFollowNotificationJSON{
    
    @JsonProperty("TopicFollowNotifications")
    private Set<TopicFollowNotificationJSON> followNots;

    @JsonCreator
    public EditResultTopicFollowNotificationJSON(@JsonProperty("ResultCode") int resultCode,
                                        @JsonProperty("ResultMessage") String resultMessage,
                                            @JsonProperty("TopicFollowNotifications") Set<TopicFollowNotificationJSON> followNotifications){
        super(resultCode, resultMessage, followNotifications.stream().map(candidate ->{
            return (FollowNotificationJSON)candidate;
        }).collect(Collectors.toSet()));
        this.followNots = followNotifications;
    }

    public Set<TopicFollowNotificationJSON> getTopicFollowNotifications(){
        return this.followNots;
    }

    public void setTopicFollowNotifications(Set<TopicFollowNotificationJSON> followNotifications){
        super.setFollowNotifications(followNotifications.stream().map(candidate ->{
            return (FollowNotificationJSON)candidate;
        }).collect(Collectors.toSet()));
        this.followNots = followNotifications;
    }
}