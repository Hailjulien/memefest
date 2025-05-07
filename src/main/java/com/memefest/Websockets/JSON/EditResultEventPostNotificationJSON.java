package com.memefest.Websockets.JSON;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditResultEventPostNotification")
public class EditResultEventPostNotificationJSON extends EditResultJSON {
    
    @JsonProperty("EventPostNotifications")
    private Set<EventPostNotificationJSON> eventPostNotifications;

    @JsonCreator
    public EditResultEventPostNotificationJSON(@JsonProperty("ResultCode") int resultCode, 
                                                @JsonProperty("ResultMessage") String resultMessage,
                                                    @JsonProperty("EventPostNotifications") Set<EventPostNotificationJSON> eventPostNotifications){
        super(Editable.POST, resultCode, resultMessage);
        this.eventPostNotifications = eventPostNotifications;                                                        
    }

    public void setEventPostNotifications(Set<EventPostNotificationJSON> eventPostNotifications){
        this.eventPostNotifications = eventPostNotifications;
    }

    public Set<EventPostNotificationJSON> getEventPostNotifications(){
        return this.eventPostNotifications;
    }

}