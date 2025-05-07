package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostJSON;

@JsonRootName("EditResultsForPost")
public class EditResultPostJSON extends EditResultJSON{
    
    @JsonProperty("Posts")
    private Set<PostJSON> posts;
    
    @JsonCreator
    public EditResultPostJSON(@JsonProperty("Posts") Set<PostJSON> posts,
                                @JsonProperty("ResultCode") int resultCode,
                                    @JsonProperty("ResultMessage") String resultMessage) {
        super(Editable.POST, resultCode, resultMessage);
        this.posts = posts;    
    }

    public Set<PostJSON> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostJSON> posts) {
        this.posts = posts;
    }
}