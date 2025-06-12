package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PostVideoId {
  @Column(name = "Vid_Id", nullable = false, updatable = false, insertable = false)
  private int vidId;
  
  @Column(name = "Post_Id", nullable = false, updatable = false, insertable = false)
  private int postId;
  
  public int getVid_Id() {
    return this.vidId;
  }
  
  public void setVid_Id(int imgId) {
    this.vidId = imgId;
  }
  
  public int getPost_Id() {
    return this.postId;
  }
  
  public void setPost_Id(int postId) {
    this.postId = postId;
  }
}

