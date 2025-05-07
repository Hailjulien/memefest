package com.memefest.Websockets.JSON;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditEventNotifications")
public class EditEventNotificationJSON extends EditJSON{
    
    @JsonProperty
    private Set<EventNotificationJSON> eventNotifications;

    @JsonCreator
    public EditEventNotificationJSON(@JsonProperty("EventNotifications") Set<EventNotificationJSON> eventNotifications){
        super(Editable.NOTIFICATION);
        this.eventNotifications = eventNotifications;
    }

    public Set<EventNotificationJSON> getEventNotifications(){
        return  this.eventNotifications;
    }

    public void setEventNotifications(Set<EventNotificationJSON> eventNotifications){
        this.eventNotifications = eventNotifications;
    }
}
