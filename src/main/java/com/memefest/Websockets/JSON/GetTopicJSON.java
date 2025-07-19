package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.TopicJSON;

@JsonRootName("GetTopic")
public class GetTopicJSON extends GetJSON{
    
    @JsonProperty("Topics")
    private Set<TopicJSON> topics;

    @JsonCreator
    public GetTopicJSON(@JsonProperty("Topics") Set<TopicJSON> topics) {
        super(Getable.TOPIC);
        this.topics = topics;
    }

    public Set<TopicJSON> getTopics() {
        return topics;
    }

    public void setTopics(Set<TopicJSON> topics) {
        this.topics = topics;
    }
}
