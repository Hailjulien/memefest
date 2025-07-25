package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostJSON;

@JsonRootName("GetResultPost")
public class GetResultPostJSON extends GetResultJSON {

    @JsonProperty("PostResult")
    private Set<PostJSON> postResults;


    @JsonCreator
    public GetResultPostJSON(@JsonProperty("ResultCode") int resultCode, @JsonProperty("ResultMessage") String resultMessage, 
                                @JsonProperty("PostResult") Set<PostJSON> postResults){
        super(Getable.POST,resultCode, resultMessage);
        this.postResults = postResults;
    }

    public Set<PostJSON> getPostResult() {
        return postResults;
    }

    public void setPostResult(Set<PostJSON> postResults) {
        this.postResults = postResults;
    }
}
