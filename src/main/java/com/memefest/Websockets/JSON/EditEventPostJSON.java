package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;

@JsonRootName("EditEventPosts")
public class EditEventPostJSON extends EditPostJSON{

    @JsonProperty("EventPosts")
    private Set<EventPostJSON> eventPosts;  

    @JsonCreator
    public EditEventPostJSON(@JsonProperty("EventPosts") Set<EventPostJSON> eventPosts){
        super(eventPosts.stream().map(candidate -> {
            return (PostJSON)candidate;
        }).collect(Collectors.toSet()));
        this.eventPosts = eventPosts;
    }

    public Set<EventPostJSON> getEventPosts(){
        return this.eventPosts;
    }

    public void setEventPosts(Set<EventPostJSON> eventPosts){
        this.eventPosts = eventPosts;
    }
}
