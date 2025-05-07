package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostJSON;

@JsonRootName("SearchPost")
public class SearchPostJSON extends SearchJSON{
    
    @JsonProperty("Post")
    private PostJSON post;

    @JsonCreator
    public SearchPostJSON(@JsonProperty("Post") PostJSON post) {
        super(Searchable.POST);
        this.post = post;
    }

    public PostJSON getPost() {
        return post;
    }

    public void setPost(PostJSON post) {
        this.post = post;
    }
}
