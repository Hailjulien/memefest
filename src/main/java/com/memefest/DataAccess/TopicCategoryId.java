package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TopicCategoryId {
    
  @Column(name = "Cat_Id", nullable = false, updatable = false, insertable = false)
  private int catId;
  
  @Column(name = "Topic_Id", nullable = false, updatable = false, insertable = false)
  private int topicId;
  
  public int getCat_Id() {
    return this.catId;
  }
  
  public void setCat_Id(int catId) {
    this.catId = catId;
  }
  
  public int getTopic_Id() {
    return this.topicId;
  }
  
  public void setTopic_Id(int topicId) {
    this.topicId = topicId;
  }
}
