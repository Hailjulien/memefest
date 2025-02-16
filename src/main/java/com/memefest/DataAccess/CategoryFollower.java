package com.memefest.DataAccess;

import com.memefest.DataAccess.CategoryFollowerId;
import com.memefest.DataAccess.Topic;
import com.memefest.DataAccess.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({@NamedQuery(name = "CategoryFollower.findByCategoryId", query = "SELECT u FROM CategoryFollowerEntity u WHERE u.id.categoryId = :categoryId"), @NamedQuery(name = "CategoryFollower.findByUserId", query = "SELECT u FROM CategoryFollowerEntity u WHERE u.id.userId = :userId")})
@Entity(name = "CategoryFollowerEntity")
@Table(name = "CATEGORY_FOLLOWS")
public class CategoryFollower {
  @EmbeddedId
  private CategoryFollowerId id;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "UserId", referencedColumnName = "UserId")
  private User user;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Cat_Id", referencedColumnName = "Cat_Id")
  private Topic topic;
  
  public User getUser() {
    return this.user;
  }
  
  public void setTopic(Topic topic) {
    this.topic = topic;
  }
  
  public void setFollower(User follower) {
    this.user = follower;
  }
  
  public User getFollower() {
    return this.user;
  }
  
  public void setCat_Id(int followerId) {
    this.id.setCat_Id(followerId);
  }
  
  public void setUserId(int userId) {
    this.id.setUserId(userId);
  }
  
  public int getCat_Id() {
    return this.id.getCat_Id();
  }
  
  public int getUserId() {
    return this.id.getUserId();
  }
}
