package com.memefest.DataAccess.JSON;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonRootName("Repost")
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class)
public class RepostJSON extends PostJSON{

    @JsonProperty("Owner")
    private UserJSON owner;

    @JsonProperty("IsCanceled")
    private boolean isCanceled;

    @JsonCreator
    public RepostJSON(@JsonProperty("PostId") int postId, @JsonProperty("Comment") String comment, 
                        @JsonProperty("Created") LocalDateTime created, 
                            @JsonProperty("Upvotes") int upvotes,
                                 @JsonProperty("Downvotes") int downvotes,
                                    @JsonProperty("User") UserJSON user,
                                        @JsonProperty("Owner") UserJSON owner, 
                                        @JsonProperty("Categories") Set<CategoryJSON>  categories,
                      @JsonProperty("CanceledCategories")Set<CategoryJSON> canceledCats) {
        super(postId,comment,created,upvotes, downvotes,user, categories,canceledCats);
        this.owner = owner;
        this.isCanceled = false;
    }


    public UserJSON getOwner() {
        return this.owner;
    }

    public void setOwner(UserJSON owner) {
        this.owner = owner;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        this.isCanceled = canceled;
    }
}
