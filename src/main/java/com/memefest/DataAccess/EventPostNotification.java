package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(name = "EventPostNotification.getEventPostNotificationByEventId", 
        query = "SELECT ePN FROM EventPostNotificationEntity ePN WHERE ePN.eventId = :eventId"),
    @NamedQuery(name = "EventPostNotification.getEventPostNotificationByPostId",
        query = "SELECT epN FROM EventPostNotificationEntity ePN WHERE ePN.id.postId = :postId")
})
@Entity(name = "EventPostNotificationEntity")
@Table(name = "EVENT_POST_NOTIFICATION")
public class EventPostNotification extends PostNotification {
 
    @Column(name = "Event_Id", nullable = false, insertable = false, updatable = false)
    private int eventId;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "Event_Id", referencedColumnName = "Event_Id")
    private Event event;

    public Event getEvent(){
        return this.event;
    }

    public void setEvent(Event event){
        this.event = event;
        this.eventId = event.getEvent_Id();
    }

    public void setEvent_Id(int eventId){
        this.eventId = eventId;
    }

    public int getEvent_Id(){
        return this.eventId;
    }
}
