package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;

@JsonRootName("GetRepost")
public class GetEventPostJSON  extends GetPostJSON{
    
    private Set<EventPostJSON> eventPosts;

    @JsonCreator
    public GetEventPostJSON(Set<EventPostJSON> eventPosts) {
        super(eventPosts.stream().map(eventEntity -> {
            return (PostJSON) eventEntity;
        }).collect(Collectors.toSet()));
        this.eventPosts = eventPosts;
    }

    public Set<EventPostJSON> getEventPosts() {
        return eventPosts;
    }

    public void setEventPosts(Set<EventPostJSON> eventPosts) {
        this.eventPosts = eventPosts;
        super.setPosts(eventPosts.stream().map(eventEntity -> {
            return (PostJSON) eventEntity;
        }).collect(Collectors.toSet()));
    }
}
