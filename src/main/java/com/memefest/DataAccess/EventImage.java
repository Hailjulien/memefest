package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries(
    {@NamedQuery(
        name = "EventImage.findByPosterId", 
        query = "SELECT u FROM EventImageEntity u WHERE u.id.posterId = :posterId"), 
    @NamedQuery(
        name = "EventImage.findByEventId", 
        query = "SELECT e FROM EventImageEntity e WHERE e.id.eventId = :eventId")
})
@Entity(name = "EventImageEntity")
@Table(name = "EVENT_IMAGE")
public class EventImage {
  @EmbeddedId
  private EventImageId id;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Event_Id", referencedColumnName = "Event_Id")
  private Event event;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Poster_Id", referencedColumnName = "Img_Id")
  private Image image;
  
  public Event getEvent() {
    return this.event;
  }
  
  public void setEvent(Event event) {
    this.event = event;
  }
  
  public void setImage(Image image) {
    this.image = image;
  }
  
  public Image getImage() {
    return this.image;
  }
  
  public void setEvent_Id(int eventId) {
    this.id.setEvent_Id(eventId);
  }
  
  public void setPoster_Id(int userId) {
    this.id.setPoster_Id(userId);
  }
  
  public int getPoster_Id() {
    return this.id.getPoster_Id();
  }
  
  public int getEvent_Id() {
    return this.id.getEvent_Id();
  }
}