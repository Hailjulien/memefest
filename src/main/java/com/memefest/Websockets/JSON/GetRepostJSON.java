package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.RepostJSON;

@JsonRootName("GetRepost")
public class GetRepostJSON extends GetJSON{
    
    @JsonProperty("Reposts")
    private Set<RepostJSON> reposts;

    @JsonCreator
    public GetRepostJSON( @JsonProperty("Reposts") Set<RepostJSON> reposts) {
        super(Getable.REPOST);
        this.reposts = reposts;
    }

    public Set<RepostJSON> getReposts() {
        return reposts;
    }

    public void setReposts(Set<RepostJSON> reposts) {
        this.reposts = reposts;
    }
}
