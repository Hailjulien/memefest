package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditResultTopicPostNotification")
public class EditResultTopicPostNotificationJSON extends EditResultJSON{
    
    @JsonProperty("TopicPostNotifications")
    private Set<TopicPostNotificationJSON> topicPostNotifications;

    @JsonCreator
    public EditResultTopicPostNotificationJSON(@JsonProperty("ResultCode") int resultCode, 
                                                @JsonProperty("ResultMessage") String resultMessage,
                                                    @JsonProperty("eventNotifications") Set<TopicPostNotificationJSON> topicPostNotifications){
        super(Editable.POST, resultCode, resultMessage);
        this.topicPostNotifications = topicPostNotifications;                                                        
    }

    public void setTopicPostNotifications(Set<TopicPostNotificationJSON> topicPostNotifications){
        this.topicPostNotifications = topicPostNotifications;
    }

    public Set<TopicPostNotificationJSON> getTopicsPostNotifications(){
        return this.topicPostNotifications;
    }

}