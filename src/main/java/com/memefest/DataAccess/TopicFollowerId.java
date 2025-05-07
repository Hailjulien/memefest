package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TopicFollowerId {
  @Column(name = "UserId", nullable = false, updatable = false, insertable = false)
  private int userId;
  
  @Column(name = "Topic_Id", nullable = false, updatable = false, insertable = false)
  private int topicId;
  
  public int getUserId() {
    return this.userId;
  }
  
  public void setUserId(int userId) {
    this.userId = userId;
  }
  
  public int getTopic_Id() {
    return this.topicId;
  }
  
  public void setTopic_Id(int topicId) {
    this.topicId = topicId;
  }
}
