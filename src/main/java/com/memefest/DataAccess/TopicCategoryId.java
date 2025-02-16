package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TopicCategoryId {
  @Column(name = "Topic_Id", nullable = false, insertable = false, updatable = false)
  private int topicId;
  
  @Column(name = "Cat_Id", nullable = false, insertable = false, updatable = false)
  private int categoryId;
  
  public int getTopic_Id() {
    return this.topicId;
  }
  
  public int getCat_Id() {
    return this.categoryId;
  }
  
  public void setTopic_Id(int topicId) {
    this.topicId = topicId;
  }
  
  public void setCat_Id(int categoryId) {
    this.categoryId = categoryId;
  }
}
