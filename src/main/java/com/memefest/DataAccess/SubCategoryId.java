package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SubCategoryId {    
  @Column(name = "Cat_Id", nullable = false, updatable = false, insertable = false)
  private int catId;
  
  @Column(name = "Parent_Id", nullable = false, updatable = false, insertable = false)
  private int parentId;
  
  public int getCat_Id() {
    return this.catId;
  }
  
  public void setCat_Id(int catId) {
    this.catId = catId;
  }
  
  public int getParent_Id() {
    return this.parentId;
  }
  
  public void setParent_Id(int parentId) {
    this.parentId = parentId;
  }
}