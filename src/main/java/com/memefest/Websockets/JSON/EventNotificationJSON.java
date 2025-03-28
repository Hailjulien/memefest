package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.EventJSON;

@JsonRootName("EventNotification")
public class EventNotificationJSON extends NotificationJSON {

    @JsonProperty("Event")
    private EventJSON event;
    
    @JsonCreator
    public EventNotificationJSON(@JsonProperty("NotificationID") int notId, 
                                @JsonProperty("Timestamp") LocalDateTime date, 
                                    @JsonProperty("Event")EventJSON event) {
        super(notId, date, Notification.EVENT);
        this.event = event;
    }

    public EventJSON getEvent() {
        return event;
    }

    public void setEvent(EventJSON event) {
        this.event = event;
    }


}
