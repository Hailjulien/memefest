package com.memefest.DataAccess;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TopicVideoId {

  @Column(name = "Vid_Id", nullable = false, updatable = false, insertable = false)
  private int vidId;
  
  @Column(name = "Topic_Id", nullable = false, updatable = false, insertable = false)
  private int topicId;
  
  public int getVid_Id() {
    return this.vidId;
  }
  
  public void setVid_Id(int vidId) {
    this.vidId = vidId;
  }
  
  public int getTopic_Id() {
    return this.topicId;
  }
  
  public void setTopic_Id(int topicId) {
    this.topicId = topicId;
  }
}
