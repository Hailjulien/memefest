package com.memefest.DataAccess.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.memefest.DataAccess.JSON.Deserialize.CustomLocalDateTimeDeserializer;
import com.memefest.DataAccess.JSON.Serialize.CustomLocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@JsonRootName("Topic")
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "TopicId")
@JsonFilter("TopicPublicView")
public class TopicJSON implements Serializable{
  @JsonProperty("TopicId")
  private int topicId;
  
  @JsonProperty("Title")
  private String title;
  
  @JsonProperty("Created")
  @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
  @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
  private LocalDateTime created;
  
  @JsonProperty("Categories")
  private Set<CategoryJSON> categories;
  
  @JsonProperty("Posts")
  private Set<TopicPostJSON> posts;
  
  @JsonProperty("FollowedBy")
  private Set<UserJSON> followedBy;
  
  @JsonProperty("CancelFollowedBy")
  private Set<UserJSON> cancelFollowedBy;
  
  @JsonProperty("CancelCategories")
  private Set<CategoryJSON> cancelCategories;
  
  @JsonProperty("Cancel")
  private boolean canceled;
  
  @JsonCreator
  public TopicJSON(@JsonProperty("TopicId") int topicId,
                    @JsonProperty("Title") String title, 
                      @JsonProperty("Created") LocalDateTime created, 
                        @JsonProperty("Categories") Set<CategoryJSON> categories, 
                          @JsonProperty("Posts") Set<TopicPostJSON> posts, 
                            @JsonProperty("FollowedBy") Set<UserJSON> followedBy) {
    this.topicId = topicId;
    this.title = title;
    this.created = created;
    this.categories = categories;
    this.posts = posts;
    this.followedBy = followedBy;
    this.canceled = false;
  }
  
  @JsonProperty("TopicId")
  public int getTopicId() {
    return this.topicId;
  }
  
  @JsonProperty("Title")
  public String getTitle() {
    return this.title;
  }
  
  @JsonProperty("Created")
  public LocalDateTime getCreated() {
    return this.created;
  }
  
  @JsonProperty("Created")
  public void setCreated(LocalDateTime created) {
    this.created = created;
  }
  
  @JsonProperty("Posts")
  public Set<TopicPostJSON> getPosts() {
    return this.posts;
  }
  
  @JsonProperty("FollowedBy")
  public Set<UserJSON> getFollowedBy() {
    return this.followedBy;
  }
  
  @JsonProperty("TopicId")
  public void setTopicId(int topicId) {
    this.topicId = topicId;
  }
  
  @JsonProperty("Title")
  public void setTitle(String title) {
    this.title = title;
  }
  
  @JsonProperty("Cancel")
  public boolean isCancelled() {
    return this.canceled;
  }
  
  @JsonProperty("Cancel")
  public void setCanceled(boolean canceled) {
    this.canceled = canceled;
  }
  
  @JsonProperty("Categories")
  public void setCategories(Set<CategoryJSON> categories) {
    this.categories = categories;
  }
  
  @JsonProperty("Categories")
  public Set<CategoryJSON> getCategories() {
    return this.categories;
  }
  
  @JsonProperty("Posts")
  public void setPosts(Set<TopicPostJSON> posts) {
    this.posts = posts;
  }
  
  @JsonProperty("FollowedBy")
  public void setFollowedBy(Set<UserJSON> followedBy) {
    this.followedBy = followedBy;
  }
  
  @JsonProperty("CancelFollowedBy")
  public Set<UserJSON> getCancelFollowedBy() {
    return this.cancelFollowedBy;
  }
  
  @JsonProperty("CancelFollowedBy")
  public void setCancelFollowedBy(Set<UserJSON> cancelFollowedBy) {
    this.cancelFollowedBy = cancelFollowedBy;
  }
  
  @JsonProperty("CancelCategories")
  public Set<CategoryJSON> getCancelCategories() {
    return this.cancelCategories;
  }
  
  @JsonProperty("CancelCategories")
  public void setCancelCategories(Set<CategoryJSON> cancelCategories) {
    this.cancelCategories = cancelCategories;
  }
}
