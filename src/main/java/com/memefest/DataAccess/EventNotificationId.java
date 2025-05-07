package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EventNotificationId {
    
    @Column(name = "UserId", nullable = false, insertable = false, updatable= false)
    private int userId;

    @Column(name = "Event_Id", nullable = false, insertable = false, updatable= false)
    private int eventId;

    public int getEvent_Id(){
        return eventId;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setEvent_Id(int eventId){
        this.eventId = eventId;
    }
}
