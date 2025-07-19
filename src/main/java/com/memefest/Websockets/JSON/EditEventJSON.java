package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.EventJSON;

@JsonRootName("EditEvent")
public class EditEventJSON extends EditJSON{

    @JsonProperty("Events")
    private Set<EventJSON> events;

    @JsonCreator
    public EditEventJSON(@JsonProperty("Events") Set<EventJSON> events) {
        super(Editable.EVENT);
        this.events = events;
    }

    public Set<EventJSON> getEvents() {
        return this.events;
    }
    public void setEvent(Set<EventJSON> events) {
        this.events = events;
    }
}
