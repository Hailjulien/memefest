package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EventVideoId {
  @Column(name = "Event_Id", nullable = false, updatable = false, insertable = false)
  private int eventId;
  
  @Column(name = "Vid_Id", nullable = false, updatable = false, insertable = false)
  private int videoId;
  
  public int getEvent_Id() {
    return this.eventId;
  }
  
  public void setEvent_Id(int userId) {
    this.eventId = userId;
  }
  
  public int getVid_Id() {
    return this.videoId;
  }
  
  public void setVid_Id(int videoId) {
    this.videoId = videoId;
  }
}

