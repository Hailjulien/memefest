package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EventPostNotificationId  extends PostNotificationId{
    
    @Column(name = "Event_Id", nullable = false, insertable = false, updatable = false)
    private int eventId;

    public int getEvent_Id(){
        return this.eventId;
    }

    public void setEvent_Id(int eventId){
        this.eventId = eventId;
    }
}
