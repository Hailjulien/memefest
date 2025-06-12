package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EventImageId {

    @Column(name = "Img_Id", nullable = false, updatable = false, insertable = false)
    private int imgId;
    
    @Column(name = "Event_Id", nullable = false, updatable = false, insertable = false)
    private int eventId;
    
    public int getEvent_Id() {
        return this.eventId;
    }
    
    public void setImg_Id(int imgId) {
        this.imgId = imgId;
    }
    
    public int getImg_Id() {
        return this.imgId;
    }
    
    public void setEvent_Id(int eventId) {
        this.eventId = eventId;
    }    
}
