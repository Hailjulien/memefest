package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EventPostNotificationId{
    
    @Column(name = "Event_Id", nullable = false, insertable = false, updatable = false)
    private int eventId;

    @Column(name = "Post_Id", nullable = false, insertable = false, updatable = false)
    private int postId;

    @Column(name = "UserId", nullable = false, insertable = false, updatable = false)
    private int recipientId;

    public int getEvent_Id(){
        return this.eventId;
    }

    public void setEvent_Id(int eventId){
        this.eventId = eventId;
    }

    public int getPost_Id(){
        return postId;
    }

    public void setPost_Id(int postId){
        this.postId = postId;
    }

    public int getUserId(){
        return this.postId;
    }

    public void setUserId(int recipientId){
        this.recipientId = recipientId;
    }
}
