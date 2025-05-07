package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.EventJSON;

@JsonRootName("SearchEvent")
public class SearchEventJSON extends SearchJSON {
    
    @JsonProperty("Event")
    private EventJSON event;

    @JsonCreator
    public SearchEventJSON(@JsonProperty("Search") SearchJSON search, @JsonProperty("Event") EventJSON event) {
        super(Searchable.EVENT);
        this.event = event;
    }

    public EventJSON getEvent() {
        return event;
    }

    public void setEvent(EventJSON event) {
        this.event = event;
    }
}
