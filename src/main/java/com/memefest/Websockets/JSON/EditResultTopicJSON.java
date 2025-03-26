package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.TopicJSON;

@JsonRootName("EditResultsForTopic")
public class EditResultTopicJSON extends EditResultJSON{
    
    @JsonProperty("Topics")
    private Set<TopicJSON> topics;

    @JsonCreator
    public EditResultTopicJSON(@JsonProperty("Topics") Set<TopicJSON> topics,
                                    @JsonProperty("ResultCode") int resultCode,
                                        @JsonProperty("ResultMessage") String resultMessage) {
        super(Editable.TOPIC, resultCode, resultMessage);
        this.topics = topics;
    }

    public Set<TopicJSON> getTopics() {
        return topics;
    }

    public void setTopics(Set<TopicJSON> topics){
        this.topics = topics;
    }
}
