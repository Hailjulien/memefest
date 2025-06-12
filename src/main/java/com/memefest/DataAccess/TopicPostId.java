package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TopicPostId {


  @Column(name = "Post_Id", nullable = false, updatable = false, insertable = false)
  private int postId;
  
  @Column(name = "Topic_Id", nullable = false, updatable = false, insertable = false)
  private int topicId;
  
  public int getPost_Id() {
    return this.postId;
  }
  
  public void setPost_Id(int postId) {
    this.postId = postId;
  }
  
  public int getTopic_Id() {
    return this.topicId;
  }
  
  public void setTopic_Id(int topicId) {
    this.topicId = topicId;
  }
}
