package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostJSON;

@JsonRootName("SearchResultsForPost")
public class SearchResultPostJSON extends SearchResultJSON{
    
    @JsonProperty("Posts")
    private Set<PostJSON> posts;

    @JsonCreator
    public SearchResultPostJSON(@JsonProperty("Posts") Set<PostJSON> posts,
                                    @JsonProperty("ResultCode") int resultCode,
                                        @JsonProperty("ResultMessage") String resultMessage) {
        super(Searchable.POST, resultCode, resultMessage);
        this.posts = posts;
    }

    public Set<PostJSON> getPosts(){
        return this.posts;
    }

    public void setPosts(Set<PostJSON> posts){
        this.posts = posts;
    }
}