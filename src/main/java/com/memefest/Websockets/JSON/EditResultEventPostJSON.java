package com.memefest.Websockets.JSON;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.EventPostJSON;

@JsonRootName("EditResultEventPost")
public class EditResultEventPostJSON extends EditResultJSON {
    
    @JsonProperty("EventPosts")
    private Set<EventPostJSON> eventPosts;

    @JsonCreator
    public EditResultEventPostJSON(@JsonProperty("ResultCode") int resultCode, 
                                                @JsonProperty("ResultMessage") String resultMessage,
                                                    @JsonProperty("EventPosts") Set<EventPostJSON> eventPosts){
        super(Editable.POST, resultCode, resultMessage);
        this.eventPosts = eventPosts;                                                        
    }

    public void setEventPosts(Set<EventPostJSON> eventPosts){
        this.eventPosts = eventPosts;
    }

    public Set<EventPostJSON> getEventPosts(){
        return this.eventPosts;
    }

}