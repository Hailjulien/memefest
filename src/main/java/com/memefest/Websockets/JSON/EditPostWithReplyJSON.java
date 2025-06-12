package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;

@JsonRootName("EditPostWithReplys")
public class EditPostWithReplyJSON extends EditJSON{

    @JsonProperty("PostsWithReplys")
    private Set<PostWithReplyJSON> posts;

    @JsonCreator
    public EditPostWithReplyJSON(@JsonProperty("PostsWithReplys")Set<PostWithReplyJSON> postsWithReplys){
        super(Editable.POST);
        posts = postsWithReplys;
    }

    public void setPostsWithReplys(Set<PostWithReplyJSON> postsWithReply){
        this.posts = postsWithReply;
    }

    public Set<PostWithReplyJSON> getPostsWithReplys(){
        return this.posts;
    }

}
