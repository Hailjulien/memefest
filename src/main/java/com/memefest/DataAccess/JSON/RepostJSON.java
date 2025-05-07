package com.memefest.DataAccess.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonRootName("Repost")
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "RepostID")
public class RepostJSON {

    @JsonProperty("RepostID")
    private int repostId;

    @JsonProperty("Post")
    private PostJSON post;

    @JsonProperty("User")
    private UserJSON user;

    @JsonProperty("IsCanceled")
    private boolean isCanceled;

    @JsonCreator
    public RepostJSON(@JsonProperty("RepostID") int repostId, @JsonProperty("Post") PostJSON post,
                        @JsonProperty("User") UserJSON user) {
        this.repostId = repostId;
        this.post = post;
        this.user = user;
        this.isCanceled = false;
    }

    public int getRepostId() {
        return this.repostId;
    }

    public void setRepostId(int repostId) {
        this.repostId = repostId;
    }

    public PostJSON getPost() {
        return this.post;
    }

    public void setPost(PostJSON post) {
        this.post = post;
    }

    public UserJSON getUser() {
        return this.user;
    }

    public void setUser(UserJSON user) {
        this.user = user;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        this.isCanceled = canceled;
    }
}
