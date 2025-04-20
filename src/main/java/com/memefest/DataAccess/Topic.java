package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Set;

@NamedNativeQueries({
  @NamedNativeQuery(
    name = "Topic.getTopicByTitle",
    query = "SELECT TOP(1) T.Topic_Id as topicId, T.Title as topicName, T.Created as created FROM TOPIC T" 
      + " WHERE T.title LIKE CONCAT('%', :title, '%')",
    resultSetMapping = "TopicEntityMapping"),
    @NamedNativeQuery(
      name = "Topic.searchTopic",
      query = "SELECT T.Topic_Id as topicId, T.Title as topicName, T.Created as created FROM TOPIC T" 
        + " WHERE T.title LIKE CONCAT('%', :title, '%')",
      resultSetMapping = "TopicEntityMapping")
})
@SqlResultSetMappings({
  @SqlResultSetMapping(
    name = "TopicEntityMapping",
    entities = {
      @EntityResult(
        entityClass = MainCategory.class, 
        fields = {
          @FieldResult(name = "topicId", column = "Topic_Id"), 
          @FieldResult(name = "topicName", column = "Title"), 
          @FieldResult(name = "created", column = "Created")
        }
      )
    }
  )
})
@NamedQueries({
  @NamedQuery(
    name = "Post.findByTopicId", 
    query = "SELECT p FROM PostEntity p WHERE p.topic.topicId = :topicId")
})
@Entity(name = "TopicEntity")
@Table(name = "TOPIC")
public class Topic {
  @Id
  @Column(name = "Topic_Id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int topicId;
  
  @Column(name = "Title")
  private String topicName;
  
  @Column(name = "Created")
  private Date created;
  
  @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "topic")
  private Set<TopicPost> posts;
  
  @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "topic")
  public Set<TopicFollower> followedBy;
  
  @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "topic")
  private Set<TopicCategory> categories;

  @OneToMany(cascade ={CascadeType.PERSIST}, mappedBy = "topic")
  private Set<TopicPostNotification> notifications;
  
  public Set<TopicFollower> getFollowedBy() {
    return this.followedBy;
  }
  
  public void setFollowedBy(Set<TopicFollower> followedBy) {
    this.followedBy = followedBy;
  }
  
  public int getTopic_Id() {
    return this.topicId;
  }
  
  public void setTopic_Id(int topicId) {
    this.topicId = topicId;
  }
  
  public Date getCreated() {
    return this.created;
  }
  
  public void setCreated(Date created) {
    this.created = created;
  }
  
  public Set<TopicCategory> getCategories() {
    return this.categories;
  }
  
  public void setPosts(Set<TopicPost> posts) {
    this.posts = posts;
  }
  
  public Set<TopicPost> getPosts() {
    return this.posts;
  }
  
  public void setCategories(Set<TopicCategory> categories) {
    this.categories = categories;
  }
  
  public String getTitle() {
    return this.topicName;
  }
  
  public void setTitle(String topicName) {
    this.topicName = topicName;
  }

  public Set<TopicPostNotification> geTopictNotifications() {
    return notifications;
  }

  public void setTopicNotifications(Set<TopicPostNotification> notifications) {
    this.notifications = notifications;
  }
}
