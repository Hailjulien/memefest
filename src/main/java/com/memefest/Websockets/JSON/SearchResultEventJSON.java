package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.EventJSON;

// Just realised should have used decorative interfaces for searchfilters ... oops 
@JsonRootName("SearchResultEvent")
public class SearchResultEventJSON extends SearchResultJSON{

    @JsonProperty("Events")
    private Set<EventJSON> events;

    @JsonCreator
    public SearchResultEventJSON(@JsonProperty("Events") Set<EventJSON> events, @JsonProperty("ResultCode")int resultCode,
                                @JsonProperty("ResultMessage") String resultMessage) {
        super(Searchable.EVENT, resultCode, resultMessage);
        this.events = events;
    }

    public Set<EventJSON> getEvents() {
        return events;
    }

    public void setEvents(Set<EventJSON> events) {
        this.events = events;
    }

}
