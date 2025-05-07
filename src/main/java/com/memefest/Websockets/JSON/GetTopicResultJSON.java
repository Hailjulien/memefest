package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.TopicJSON;

@JsonRootName("GetTopicResult")
public class GetTopicResultJSON extends ResultJSON {
    
    @JsonProperty("Topics")
    private Set<TopicJSON> topics;

    @JsonCreator
    public GetTopicResultJSON(@JsonProperty("ResultCode") int resultCode, @JsonProperty("ResultMessage") String message,
                                @JsonProperty("Topics") Set<TopicJSON> topics) {
        super(resultCode, message);
        this.topics = topics;
    }

    public void setTopics(Set<TopicJSON> topics){
        this.topics = topics;
    }

    public Set<TopicJSON> getTopics(){
        return this.topics;
    }
}
