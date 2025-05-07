package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetResultEventPostNotification")
public class GetResultEventPostNotificationJSON extends GetResultPostNotificationJSON{
    
    @JsonProperty("EventPostNotifications")
    private Set<EventPostNotificationJSON> eventPostNotifications;

    @JsonCreator
    public GetResultEventPostNotificationJSON(@JsonProperty("ResultCode") int resultCode,
                                    @JsonProperty("ResultMessage") String resultMessage,
                                    @JsonProperty("EventPostNotifications") Set<EventPostNotificationJSON> eventPostNotifications){
        super(resultCode, resultMessage, eventPostNotifications.stream().map(candidate ->{
            return (PostNotificationJSON) candidate;
        }).collect(Collectors.toSet()));
    }

    public void setEventPostNotifications(Set<EventPostNotificationJSON> eventPostNotifications){
        this.eventPostNotifications = eventPostNotifications;
        super.setPostNotifications(eventPostNotifications.stream().map(candidate -> {
            return (PostNotificationJSON) candidate;
        }).collect(Collectors.toSet()));
    }

    public Set<EventPostNotificationJSON> getEventPostNotifications(){
        return this.eventPostNotifications;
    }                               
}