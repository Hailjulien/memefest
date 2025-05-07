package com.memefest.Websockets.JSON;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditResultsForPostNotification")
public class EditResultPostNotificationJSON extends EditResultJSON{
    
    @JsonProperty("PostNotifications")
    private Set<PostNotificationJSON> posts;
    
    @JsonCreator
    public EditResultPostNotificationJSON(@JsonProperty("PostNotifications") Set<PostNotificationJSON> postNotifications,
                                @JsonProperty("ResultCode") int resultCode,
                                    @JsonProperty("ResultMessage") String resultMessage) {
        super(Editable.POST, resultCode, resultMessage);
        this.posts = postNotifications;    
    }

    public Set<PostNotificationJSON> getPostNotifications() {
        return posts;
    }

    public void setPostNotifications(Set<PostNotificationJSON> posts) {
        this.posts = posts;
    }
}
