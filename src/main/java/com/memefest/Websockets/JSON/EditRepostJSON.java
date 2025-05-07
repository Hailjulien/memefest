package com.memefest.Websockets.JSON;   
import com.memefest.DataAccess.JSON.RepostJSON;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Set;

@JsonRootName("EditRepost")
public class EditRepostJSON extends EditJSON{

    @JsonProperty("Posts")
    private Set<RepostJSON> reposts; 

    @JsonCreator
    public EditRepostJSON(@JsonProperty("Reposts") Set<RepostJSON> reposts) {
        super(Editable.REPOST);
        this.reposts = reposts;
    }

    public Set<RepostJSON> getPosts(){
        return this.reposts;
    }

    public void setPosts(Set<RepostJSON> reposts){
        this.reposts = reposts;
    }
}
