package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetResultEventNotification")
public class GetResultEventNotificationJSON  extends GetResultNotificationJSON {
    
    @JsonProperty("EventNotifications")
    private Set<EventNotificationJSON> eventNotifications;

    @JsonCreator
    public GetResultEventNotificationJSON(@JsonProperty("ResultCode") int resultCode,
                            @JsonProperty("ResultMessage") String resultMessage,
                            @JsonProperty("EventNotifications") Set<EventNotificationJSON> eventNotifications){
        super(resultCode, resultMessage, eventNotifications.stream().map(candidate ->{
            return (NotificationJSON) candidate;
        }).collect(Collectors.toSet()));
    }

    public Set<EventNotificationJSON> getEventNotifications(){
        return this.eventNotifications;
    }

    public void setEventNotifications(Set<EventNotificationJSON> eventNotifications){
        this.eventNotifications = eventNotifications;
        eventNotifications.stream().map(candidate ->{
            return (NotificationJSON)candidate;
        }).collect(Collectors.toSet());
    }
}
