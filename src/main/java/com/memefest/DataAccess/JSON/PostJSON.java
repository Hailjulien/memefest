package com.memefest.DataAccess.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.memefest.DataAccess.JSON.Deserialize.CustomLocalDateTimeDeserializer;
import com.memefest.DataAccess.JSON.Serialize.CustomLocalDateTimeSerializer;
import java.time.LocalDateTime;

@JsonRootName("Post")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "PostId")
public class PostJSON {
  @JsonProperty("PostId")
  private int postId;
  
  @JsonProperty("Comment")
  private String comment;
  
  @JsonProperty("Created")
  @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
   @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
  private LocalDateTime created;
  
  @JsonProperty("Upvotes")
  private int upvotes;
  
  @JsonProperty("Downvotes")
  private int downvotes;

  @JsonProperty("User")
  private UserJSON user;
  
  @JsonProperty("Cancel")
  private boolean canceled;
  
  @JsonCreator
  public PostJSON(@JsonProperty("PostId") int postId, @JsonProperty("Comment") String comment, @JsonProperty("Created") LocalDateTime created, @JsonProperty("Upvotes") int upvotes, @JsonProperty("Downvotes") int downvotes, @JsonProperty("User") UserJSON user) {
    this.postId = postId;
    this.comment = comment;
    this.created = created;
    this.upvotes = upvotes;
    this.user = user;
  }
  
  @JsonProperty("PostId")
  public int getPostId() {
    return this.postId;
  }
  
  @JsonProperty("Comment")
  public String getComment() {
    return this.comment;
  }
  
  @JsonProperty("Created")
  public LocalDateTime getCreated() {
    return this.created;
  }
  
  @JsonProperty("Upvotes")
  public int getUpvotes() {
    return this.upvotes;
  }
  
  @JsonProperty("Downvotes")
  public int getDownvotes() {
    return this.downvotes;
  }
  
  @JsonProperty("User")
  public UserJSON getUser() {
    return this.user;
  }
  
  @JsonProperty("PostId")
  public void setPostId(int postId) {
    this.postId = postId;
  }
  
  @JsonProperty("Comment")
  public void setComment(String comment) {
    this.comment = comment;
  }
  
  @JsonProperty("Created")
  public void setCreated(LocalDateTime created) {
    this.created = created;
  }
  
  @JsonProperty("Upvotes")
  public void setUpvotes(int upvotes) {
    this.upvotes = upvotes;
  }
  
  @JsonProperty("Downvotes")
  public void setDownvotes(int downvotes) {
    this.downvotes = downvotes;
  }
  
  @JsonProperty("Cancel")
  public boolean isCancelled() {
    return this.canceled;
  }
  
  @JsonProperty("Cancel")
  public void setCanceled(boolean canceled) {
    this.canceled = canceled;
  }
  
  @JsonProperty("User")
  public void setUser(UserJSON user) {
    this.user = user;
  }
  

}
