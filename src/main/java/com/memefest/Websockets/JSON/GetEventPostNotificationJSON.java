package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetEventPostNotification")
public class GetEventPostNotificationJSON extends GetPostNotificationJSON{

    @JsonProperty("EventPostNotifications")
    public Set<EventPostNotificationJSON> eventPostNotifications;

    @JsonCreator
    public GetEventPostNotificationJSON(@JsonProperty("EventPostNotifications") Set<EventPostNotificationJSON> eventPostNotifications){
        super(eventPostNotifications.stream().map(candidate ->{
            return (PostNotificationJSON)candidate;
        }).collect(Collectors.toSet()));
        this.eventPostNotifications = eventPostNotifications;
    }

    public void setEventPostNotifications(Set<EventPostNotificationJSON> eventPostNotifications){
        this.eventPostNotifications = eventPostNotifications;
        super.setPostNotifications(eventPostNotifications.stream().map(candidate ->{
            return (PostNotificationJSON)candidate;
        }).collect(Collectors.toSet()));
    }

    public Set<EventPostNotificationJSON> getEventPostNotifications(){
        return this.eventPostNotifications;
    }
    
}
