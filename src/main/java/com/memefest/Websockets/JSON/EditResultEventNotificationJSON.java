package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditResultEventNotification")
public class EditResultEventNotificationJSON  extends EditResultJSON {
    
    @JsonProperty("EventNotifications")
    private Set<EventNotificationJSON> eventNotifications;

    @JsonCreator
    public EditResultEventNotificationJSON(@JsonProperty("ResultCode") int resultCode, 
                                                @JsonProperty("ResultMessage") String resultMessage,
                                                    @JsonProperty("EventNotifications") Set<EventNotificationJSON> eventNotifications){
        super(Editable.NOTIFICATION, resultCode, resultMessage);
        this.eventNotifications = eventNotifications;                                                        
    }

    @JsonProperty("EventNotifications")
    public void setEventNotifications(Set<EventNotificationJSON> eventNotifications){
        this.eventNotifications = eventNotifications;
    }

    @JsonProperty("EventNotifications")
    public Set<EventNotificationJSON> getEventNotifications(){
        return this.eventNotifications;
    }
}
