package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;

@JsonRootName("GetPostReplies")
public class GetPostReplysJSON extends GetJSON {

    @JsonProperty("PostWithReplies")
    private Set<PostWithReplyJSON> posts;

    @JsonCreator
    public GetPostReplysJSON(@JsonProperty("PostsWithReplies") Set<PostWithReplyJSON> postsWithReplys){
        super(Getable.POST);
        this.posts = postsWithReplys;
    }

    public Set<PostWithReplyJSON> getPostWithReplys(){
        return this.posts;
    }

    public void setPostsWithReplys(Set<PostWithReplyJSON> postWithReplys){
        this.posts = postWithReplys;
    }
}
