package com.memefest.DataAccess;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EventPostId {
    @Column(name = "Event_Id", nullable = false, updatable = false, insertable = false)
    private int eventId;
    
    @Column(name = "Post_Id", nullable = false, updatable = false, insertable = false)
    private int postId;
    
    public int getEvent_Id() {
        return this.eventId;
    }
    
    public void setEvent_Id(int eventId) {
        this.eventId = eventId;
    }
    
    public int getPost_Id() {
        return this.postId;
    }
    
    public void setPost_Id(int postId) {
        this.postId = postId;
    }    
}
