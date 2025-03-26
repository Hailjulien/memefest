package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;

@JsonRootName("EventPostNotification")
public class EventPostNotification extends PostNotificationJSON{
    
    @JsonProperty("EventPost")
    private EventPostJSON eventPost;

    @JsonCreator
    public EventPostNotification(@JsonProperty("NotificationID") int notId,
                                    @JsonProperty("EventPost") EventPostJSON eventPost,
                                        @JsonProperty("TimeStamp") LocalDateTime time) {
        super(notId, (PostJSON) eventPost, time);
    }   

    public EventPostJSON getEventPost() {
        return eventPost;
    }

    public void setEventPost(EventPostJSON eventPost) {
        this.eventPost = eventPost;
    }

}
