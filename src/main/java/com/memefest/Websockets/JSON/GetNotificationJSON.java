package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetNotification")
public class GetNotificationJSON extends GetJSON {
    
    @JsonProperty("Notifications")
    private Set<NotificationJSON> notifications;

    @JsonCreator
    public GetNotificationJSON(@JsonProperty("Notifications") Set<NotificationJSON> notifications){
        super(Getable.NOTIFICATION);
        this.notifications = notifications;
    }

    public Set<NotificationJSON> getNotifications(){
        return this.notifications;
    }

    public void setNotifications(Set<NotificationJSON> notifications){
        this.notifications = notifications;
    }
}
