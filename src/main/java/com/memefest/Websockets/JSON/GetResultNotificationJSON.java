package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetResultNotification")
public class GetResultNotificationJSON  extends GetResultJSON {

    @JsonProperty("Notifications")
    private Set<NotificationJSON> notifications;

    @JsonCreator
    public GetResultNotificationJSON(@JsonProperty("ResultCode") int resultCode,
                                        @JsonProperty("ResultMessage") String resultMessage,
                                            @JsonProperty("Notifications") Set<NotificationJSON> notifications){
        super(Getable.NOTIFICATION, resultCode, resultMessage);
        this.notifications = notifications; 
    }

    public void setNotifications(Set<NotificationJSON> notifications){
        this.notifications = notifications;
    }

    public Set<NotificationJSON> getNotifications(){
        return this.notifications;
    }
}
