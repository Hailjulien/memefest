package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.TopicJSON;

@JsonRootName("EditScheduledEvent")
public class GetScheduledEventJSON  extends GetJSON{
    
    @JsonProperty("Events")
    //@JsonSerialize(keyAs = EventJSON.class)
    //JsonSerialize(keyUsing = MapSerializer.class)
    private Set<EventJSON> events; 

    public GetScheduledEventJSON(@JsonProperty("Events") Set<EventJSON> events){
        super(Getable.EVENT);
        this.events = events;
    }

    public void setEvents(Set<EventJSON> events){
        this.events = events;
    }

    public Set<EventJSON> getEvents(){
        return this.events;
    }
}
