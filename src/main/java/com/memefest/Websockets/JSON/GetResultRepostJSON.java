package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.RepostJSON;

@JsonRootName("GetRepostResults")
public class GetResultRepostJSON extends ResultJSON{
    
    @JsonProperty("Reposts")
    private Set<RepostJSON> reposts;

    @JsonCreator
    public GetResultRepostJSON(@JsonProperty("ResultCode") int resultCode, @JsonProperty("ResultMessage")String resultMessage,
                                @JsonProperty("Reposts") Set<RepostJSON> reposts) {
        super(resultCode, resultMessage);
        this.reposts = reposts;
    }

    public Set<RepostJSON> getReposts() {
        return reposts;
    }

    public void setReposts(Set<RepostJSON> reposts) {
        this.reposts = reposts;
    }
}
