package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditEventPostNotifications")
public class EditEventPostNotificationJSON extends EditJSON{
    
    @JsonProperty("EventPostNotifications")
    private Set<EventPostNotificationJSON> eventPostNots;

    @JsonCreator
    public EditEventPostNotificationJSON(@JsonProperty("EventPostNotifications") Set<EventPostNotificationJSON> eventPostNotifications){
        super(Editable.NOTIFICATION);
        this.eventPostNots = eventPostNotifications;
    }
    public Set<EventPostNotificationJSON> getEventPostNotifications(){
        return this.eventPostNots;
    }
}
