package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetEventNotification")
public class GetEventNotificationJSON extends GetNotificationJSON{
    
    @JsonProperty("EventNotifications")
    private Set<EventNotificationJSON> eventNotifications;

    @JsonCreator
    public GetEventNotificationJSON(@JsonProperty("EventNotifications") Set<EventNotificationJSON> eventNotifications){
        super(eventNotifications.stream().map(candidate ->{
            return (NotificationJSON)candidate;
        }).collect(Collectors.toSet()));
        this.eventNotifications = eventNotifications;
    }

    public Set<EventNotificationJSON> getEventNotifications(){
        return this.eventNotifications;
    }

    public void setEventNotifications(Set<EventNotificationJSON> eventNotifications){
        super.setNotifications(eventNotifications.stream().map(candidate ->{
            return (NotificationJSON)candidate;
        }).collect(Collectors.toSet()));
    }
}
