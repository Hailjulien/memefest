package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.EventJSON;

@JsonRootName("GetEventResult")
public class GetResultEventJSON extends ResultJSON {
    
    @JsonProperty("Events")
    private Set<EventJSON> eventResults;

    @JsonCreator
    public GetResultEventJSON(@JsonProperty("ResultCode") int resultCode, @JsonProperty("ResultMessage") String resultMessage,
                                @JsonProperty("Events") Set<EventJSON> events){
        super(resultCode,resultMessage);
        this.eventResults = events;
    }

    public Set<EventJSON> getEventResults() {
        return eventResults;
    }

    public void setEventResults(Set<EventJSON> eventResults) {
        this.eventResults = eventResults;
    }
}
