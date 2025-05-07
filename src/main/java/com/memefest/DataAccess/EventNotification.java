package com.memefest.DataAccess;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries(
    {
        @NamedQuery(
            name = "EventNotification.findByEventId",
            query = "SELECT f FROM EventNotificationEntity f WHERE f.id.eventId = :eventId"
        ),
        @NamedQuery(
            name = "EventNotification.findByUserId",
            query = "SELECT f FROM EventNotificationEntity f WHERE f.id.userId = :userId"
        )
    }
)
@Entity(name = "EventNotificationEntity")
@Table(name = "EVENT_NOTIFICATION")
public class EventNotification {
    
    @EmbeddedId
    private EventNotificationId id;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "Event_Id", referencedColumnName = "Event_Id")
    private Event event;

    public User getUser(){
        return this.user;
    }

    public Event getEvent(){
        return this.event;
    }

    public void setEvent_Id(int eventId){
        this.id.setEvent_Id(eventId);
    }

    public void setUserId(int userId){
        this.id.setUserId(userId);
    }

    public int getEvent_Id(){
        return this.id.getEvent_Id();
    }

    public int getUserId(){
        return this.id.getUserId();
    }

    public void setUser(User user){
        this.user = user;
        setUserId(user.getUserId());
    }

    public void setEvent(Event event){
        this.event = event;
        setEvent_Id(event.getEvent_Id());   
    }
}
