package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;



@Embeddable
public class EventImageId {
  @Column(name = "Event_Id", nullable = false, updatable = false, insertable = false)
  private int eventId;
  
  @Column(name = "Poster_Id", nullable = false, updatable = false, insertable = false)
  private int posterId;
  
  public int getEvent_Id() {
    return this.eventId;
  }
  
  public void setEvent_Id(int eventId) {
    this.eventId = eventId;
  }
  
  public int getPoster_Id() {
    return this.posterId;
  }
  
  public void setPoster_Id(int posterId) {
    this.posterId = posterId;
  }
}
