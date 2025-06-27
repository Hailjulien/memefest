package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.EventJSON;

@JsonRootName("GetEvents")
public class GetEventJSON extends GetJSON{
    
    @JsonProperty("Events")
    private Set<EventJSON> events;

    @JsonCreator
    public GetEventJSON(@JsonProperty("Events") Set<EventJSON> events) {
        super(Getable.EVENT);
        this.events = events;
    }

    public Set<EventJSON> getEvents(){
        return events;
    }

    public void setEvents(Set<EventJSON> events){
        this.events = events;
    }
}
