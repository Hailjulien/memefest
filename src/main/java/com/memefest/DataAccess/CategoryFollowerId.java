package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CategoryFollowerId {
  @Column(name = "UserId", nullable = false, updatable = false, insertable = false)
  private int userId;
  
  @Column(name = "Cat_Id", nullable = false, updatable = false, insertable = false)
  private int categoryId;
  
  public int getUserId() {
    return this.userId;
  }
  
  public void setUserId(int userId) {
    this.userId = userId;
  }
  
  public int getCat_Id() {
    return this.categoryId;
  }
  
  public void setCat_Id(int categoryId) {
    this.categoryId = categoryId;
  }
}
