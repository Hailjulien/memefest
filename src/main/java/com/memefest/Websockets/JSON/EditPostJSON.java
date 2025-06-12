package com.memefest.Websockets.JSON;

import com.memefest.DataAccess.JSON.PostJSON;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public class EditPostJSON extends EditJSON{

    @JsonProperty("Posts")
    private Set<PostJSON> posts; 

    @JsonCreator
    public EditPostJSON(@JsonProperty("Posts")Set<PostJSON> posts) {
        super(Editable.POST);
        this.posts = posts;
    }

    public Set<PostJSON> getPosts(){
        return this.posts;
    }

    public void setPosts(Set<PostJSON> posts){
        this.posts = posts;
    }
}
