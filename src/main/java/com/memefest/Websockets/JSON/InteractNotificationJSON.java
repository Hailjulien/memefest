package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.UserJSON;

@JsonRootName("InteractNotification")
public class InteractNotificationJSON extends NotificationJSON{

    @JsonProperty("InteractionType")
    private InteractionType interaction;

    @JsonProperty("Post")
    private PostJSON post;

    @JsonCreator
    public InteractNotificationJSON(@JsonProperty("NotificationID") int notId,
                                        @JsonProperty("Post") PostJSON postJSON, 
                                            @JsonProperty("TimeStamp") LocalDateTime time, 
                                                @JsonProperty("InteractionType") InteractionType interactionType, UserJSON user){
        super(notId, time, Notification.INTERACT,user);
        this.interaction = interactionType;

    }

    public int getInteractionCount(){
        switch (this.interaction) {
            case UPVOTE:
                
                return getPost().getDownvotes();
            case DOWNVOTE:
                
                return getPost().getUpvotes();
            default:
            
                return 0;
        }
    }

    public PostJSON getPost(){
        return this.post;
    }
}
