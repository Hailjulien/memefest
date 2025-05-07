package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserFollowerId {
  @Column(name = "UserId", nullable = false, updatable = false, insertable = false)
  private int userId;
  
  @Column(name = "Follower_Id", nullable = false, updatable = false, insertable = false)
  private int followerId;
  
  public int getUserId() {
    return this.userId;
  }
  
  public void setUserId(int userId) {
    this.userId = userId;
  }
  
  public int getFollowerId() {
    return this.followerId;
  }
  
  public void setFollowerId(int followerId) {
    this.followerId = followerId;
  }
}
