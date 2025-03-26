package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(
        name = "EventVideo.findByEventId",
        query = "SELECT e FROM EventVideoEntity e WHERE e.id.eventId = :eventId"),
    @NamedQuery(
        name = "EventVideo.findByVidId", 
        query = "SELECT e FROM EventVideoEntity e WHERE e.id.videoId = :videoId")
})
@Entity(name = "EventVideoEntity")
@Table(name = "EVENT_VIDEO")
public class EventVideo {
  @EmbeddedId
  private EventVideoId id;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Event_Id", referencedColumnName = "Event_Id")
  private Event event;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Vid_Id", referencedColumnName = "Vid_Id")
  private Video video;
  
  public Event getEvent() {
    return this.event;
  }
  
  public void setEvent(Event event) {
    this.event = event;
  }
  
  public void setVideo(Video video) {
    this.video = video;;
  }
  
  public Video getVideo() {
    return this.video;
  }
  
  public void setEvent_Id(int eventId) {
    this.id.setEvent_Id(eventId);
  }
  
  public void setVid_Id(int userId) {
    this.id.setVid_Id(userId);
  }
  
  public int getVid_Id() {
    return this.id.getVid_Id();
  }
  
  public int getEvent_Id() {
    return this.id.getEvent_Id();
  }
}