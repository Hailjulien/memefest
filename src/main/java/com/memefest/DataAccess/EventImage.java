package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries(
    {@NamedQuery(
        name = "EventImage.findByPosterId", 
        query = "SELECT u FROM EventImageEntity u WHERE u.eventId = :eventId"), 
    @NamedQuery(
        name = "EventImage.findByEventId", 
        query = "SELECT e FROM EventImageEntity e WHERE e.eventId = :eventId")
})
@Entity(name = "EventImageEntity")
@Table(name = "EVENT_IMAGE")
public class EventImage extends Image{
  
  @Column(name = "Event_Id", nullable = false, updatable = false, insertable = false)
  private int eventId; 

  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Event_Id", referencedColumnName = "Event_Id")
  private Event event;
  
  public Event getEvent() {
    return this.event;
  }
  
  public void setEvent(Event event) {
    this.event = event;
  }
    
  public void setEvent_Id(int eventId) {
    this.eventId = eventId;
  }

  public int getEvent_Id() {
    return this.eventId;
  }
}