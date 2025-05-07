package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;

@JsonRootName("GetEventResult")
public class GetResultEventPostJSON extends GetResultPostJSON{
    
    @JsonProperty("EventPosts")
    private Set<EventPostJSON> eventPosts;

    @JsonCreator
    public GetResultEventPostJSON(@JsonProperty("ResultCode") int resultCode, @JsonProperty("ResultMessage") String resultMessage,
                                    @JsonProperty("ResultPosts") Set<EventPostJSON> eventPosts){
        super(resultCode, resultMessage, eventPosts.stream().map(eventInst ->{
                                            return (PostJSON)eventInst;
                                        }).collect(Collectors.toSet()));
        this.eventPosts = eventPosts;
    }
    public void setEventPosts(Set<EventPostJSON> eventPosts) {
        this.eventPosts = eventPosts;
    }
    
    public Set<EventPostJSON> getEventPosts() {
        return eventPosts;
    }
}
