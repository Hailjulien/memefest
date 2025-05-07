package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.EventJSON;

@JsonRootName("EditResultEvent")
public class EditResultEventJSON extends EditResultJSON{
    
    @JsonProperty("Events")
    private Set<EventJSON> events;

    public EditResultEventJSON(@JsonProperty("Events") Set<EventJSON> events,
                                    @JsonProperty("ResultCode") int resultCode, 
                                        @JsonProperty("ResultMessage") String resultMessage) {
        super(Editable.EVENT, resultCode, resultMessage);
    }
        
    public Set<EventJSON> getEvents(){
        return this.events;
    }

    public void setEvents(Set<EventJSON> events){
        this.events = events;
    }

}
