package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostJSON;

@JsonRootName("GetPost")
public class GetPostJSON extends GetJSON{
    
    @JsonProperty("Posts")
    private Set<PostJSON> posts;

    @JsonCreator
    public GetPostJSON(@JsonProperty("Posts") Set<PostJSON> posts) {
        super(Getable.POST);
        this.posts = posts;
    }

    public Set<PostJSON> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostJSON> posts) {
        this.posts = posts;
    }
}
