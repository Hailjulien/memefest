package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TopicImageId {

  @Column(name = "Img_Id", nullable = false, updatable = false, insertable = true)
  private int imgId;
  
  @Column(name = "Topic_Id", nullable = false, updatable = false, insertable = false)
  private int topicId;
  
  public int getPoster_Id() {
    return this.imgId;
  }
  
  public void setPoster_Id(int imgId) {
    this.imgId = imgId;
  }
  
  public int getTopic_Id() {
    return this.topicId;
  }
  
  public void setTopic_Id(int topicId) {
    this.topicId = topicId;
  }
}
