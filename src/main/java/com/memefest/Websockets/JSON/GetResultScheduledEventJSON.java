package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.TopicJSON;

public class GetResultScheduledEventJSON extends GetResultJSON {
    
    @JsonProperty("EventTimes")
    @JsonSerialize(keyAs = EventJSON.class)
    //JsonSerialize(keyUsing = MapSerializer.class)
    private Map<EventJSON, LocalDateTime> eventTimes; 

    public GetResultScheduledEventJSON(@JsonProperty("ResultCode") int resultCode, 
                                        @JsonProperty("ResultMessage") String resultMessage,
                                            @JsonProperty("EventTimes") Map<EventJSON,LocalDateTime> eventTimes){
        super(Getable.EVENT, resultCode, resultMessage);
        this.eventTimes = eventTimes;
    }

    public Map<EventJSON, LocalDateTime> getEventTimes(){
        return this.eventTimes;
    }

    public void setEventTimes(Map<EventJSON, LocalDateTime> eventTimes){
        this.eventTimes = eventTimes;
    }
}