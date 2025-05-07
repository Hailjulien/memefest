package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditPostNotifications")
public class EditPostNotificationJSON extends EditJSON{
    
    @JsonProperty("PostNotifications")
    private Set<PostNotificationJSON> postNotifications;

    @JsonCreator
    public EditPostNotificationJSON(@JsonProperty("PostNotifications") Set<PostNotificationJSON> postNotifications){
        super(Editable.NOTIFICATION);
        this.postNotifications = postNotifications;
    }

    public void setPostNotifications(Set<PostNotificationJSON> postNotifications){
        this.postNotifications = postNotifications;
    }

    public Set<PostNotificationJSON> getPostNotifications(){
        return this.postNotifications;
    }
}
