package com.memefest.DataAccess.JSON;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventPostJSON extends PostJSON {

    @JsonProperty("Event")
    private EventJSON event;

    @JsonCreator
     public EventPostJSON(@JsonProperty("PostId") int postId, 
                            @JsonProperty("Comment") String comment,
                                @JsonProperty("Created") LocalDateTime created, 
                                    @JsonProperty("Upvotes") int upvotes, 
                                        @JsonProperty("Downvotes") int downvotes, 
                                            @JsonProperty("User") UserJSON user, 
                                                @JsonProperty("Event") EventJSON event,
                                    @JsonProperty("Categories") Set<CategoryJSON> categories,
                                    @JsonProperty("CanceledCategories")Set<CategoryJSON> canceledCats) {
        super(postId, comment,created,upvotes, downvotes, user, categories, canceledCats);
        this.event = event;
    }

@JsonProperty("Event")
public EventJSON getEvent() {
        return this.event;
  }

  @JsonProperty("Event")
  public void setEvent(EventJSON event) {
    this.event = event;
  }
  
  
  


}
