package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.memefest.DataAccess.JSON.Deserialize.CustomLocalDateTimeDeserializer;
import com.memefest.DataAccess.JSON.Serialize.CustomLocalDateTimeSerializer;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "NotificationID")
@JsonRootName("Notification")
public class NotificationJSON {
    
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonProperty("Timestamp")
    private LocalDateTime date;

    @JsonProperty("NotificationType")
    private Notification notification;  

    @JsonProperty("NotificationID")
    private int notId;
    
    @JsonCreator
    public NotificationJSON(@JsonProperty("NotificationID") int notId, 
                                @JsonProperty("Timestamp") LocalDateTime date,
                                     @JsonProperty("NotificationType") Notification notification){
        this.date = date;
        this.notification = notification;
    }

    public LocalDateTime getDate(){
        return date;
    }

    public void setDate(LocalDateTime date){
        this.date = date;
    }

    public Notification getNotification(){
        return notification;
    }

    public void setNotificationId(int Id){
        this.notId = Id;
    }

    public int getNotificationId(){
        return notId;
    }

}
