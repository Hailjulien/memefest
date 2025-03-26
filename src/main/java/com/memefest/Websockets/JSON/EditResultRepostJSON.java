package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.RepostJSON;

@JsonRootName("EditResultForRepost")
public class EditResultRepostJSON extends EditResultJSON{
    
    @JsonProperty("Reposts")
    private Set<RepostJSON> reposts;
    
    public EditResultRepostJSON(Set<RepostJSON> reposts, String resultMessage, int resultCode) {
        super(Editable.REPOST,  resultCode, resultMessage);
        this.reposts = reposts;
    }

    public Set<RepostJSON> getReposts() {
        return reposts;
    }
    public void setReposts(Set<RepostJSON> reposts) {
        this.reposts = reposts;
    }
}
