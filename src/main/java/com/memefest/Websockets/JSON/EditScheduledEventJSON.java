package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.memefest.DataAccess.JSON.EventJSON;

@JsonRootName("EditScheduledEvent")
public class EditScheduledEventJSON extends EditJSON{
    
    @JsonProperty("EventTimes")
    @JsonSerialize(keyAs = EventJSON.class)
    //JsonSerialize(keyUsing = MapSerializer.class)
    private Map<EventJSON, LocalDateTime> eventTimes; 

    @JsonCreator
    public EditScheduledEventJSON(@JsonProperty("EventTimes")Map<EventJSON,LocalDateTime> eventTimes){
        super(Editable.EVENT);
        this.eventTimes = eventTimes;
    }

    public void setEventTimes(Map<EventJSON, LocalDateTime> eventTimes){
        this.eventTimes = eventTimes;
    }

    public Map<EventJSON, LocalDateTime> getEventTimes(){
        return this.eventTimes;
    }
}
