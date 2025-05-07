package com.memefest.DataAccess.JSON;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TopicPostJSON extends PostJSON {

    @JsonProperty("Topic")
    private TopicJSON topic;

      @JsonCreator
  public TopicPostJSON(@JsonProperty("PostId") int postId, @JsonProperty("Comment") String comment, @JsonProperty("Created") LocalDateTime created, @JsonProperty("Upvotes") int upvotes, @JsonProperty("Downvotes") int downvotes, @JsonProperty("User") UserJSON user, @JsonProperty("Topic") TopicJSON topic) {
    super(postId, comment,created,upvotes, downvotes, user);
    this.topic = topic;
  }

  @JsonProperty("Topic")
  public TopicJSON getTopic() {
    return this.topic;
  }

  @JsonProperty("Topic")
  public void setTopic(TopicJSON topic) {
    this.topic = topic;
  }
    
}
