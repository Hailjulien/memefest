package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetResultPostNotification")
public class GetResultPostNotificationJSON  extends GetResultNotificationJSON{
    
    @JsonProperty("PostNotifications")
    private Set<PostNotificationJSON> postNotifications;

    @JsonCreator
    public  GetResultPostNotificationJSON(@JsonProperty("ResultCode") int resultCode,
                                            @JsonProperty("ResultMessage") String resultMessage,
                                                @JsonProperty("PostNotifications") Set<PostNotificationJSON> postNotifications){
        super(resultCode, resultMessage, postNotifications.stream().map(candidate ->{
            return (NotificationJSON)candidate;
        }).collect(Collectors.toSet()));
        this.postNotifications = postNotifications;
    }

    public void setPostNotifications(Set<PostNotificationJSON> postNotifications){
        this.postNotifications = postNotifications;
        super.setNotifications(postNotifications.stream().map(candidate -> {
            return (NotificationJSON) candidate;
        }).collect(Collectors.toSet()));
    }

    public Set<PostNotificationJSON> getPostNotifications(Set<PostNotificationJSON> postNotifications){
        return postNotifications;
    }

}
