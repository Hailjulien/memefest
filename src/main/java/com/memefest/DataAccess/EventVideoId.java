package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EventVideoId {
    
    @Column(name = "Vid_Id", nullable = false, updatable = false, insertable = false)
    private int vidId;
    
    @Column(name = "Event_Id", nullable = false, updatable = false, insertable = false)
    private int eventId;
    
    public int getEvent_Id() {
        return this.eventId;
    }
    
    public void setVid_Id(int vidId) {
        this.vidId = vidId;
    }
    
    public int getVid_Id() {
        return this.vidId;
    }
    
    public void setEvent_Id(int eventId) {
        this.eventId = eventId;
    }    
}
