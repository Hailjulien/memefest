package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PostReplyId {    

  @Column(name = "Post_Id", nullable = false, updatable = false, insertable = false)
  private int postId;
  
  @Column(name = "Post_Info", nullable = false, updatable = false, insertable = false)
  private int parentId;
  
  public int getPost_Id() {
    return this.postId;
  }
  
  public void setPost_Id(int postId) {
    this.postId = postId;
  }
  
  public int getPost_Info() {
    return this.parentId;
  }
  
  public void setPost_Info(int parentId) {
    this.parentId = parentId;
  }
}