package com.memefest.Websockets.JSON;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.TopicJSON;

@JsonRootName("SearchResultsForTopic")
public class SearchResultTopicJSON extends SearchResultJSON {
    
    @JsonProperty("Topics")
    private Set<TopicJSON> topics;
    
    @JsonCreator
    public SearchResultTopicJSON(@JsonProperty("Topics") Set<TopicJSON> topics, 
                                    @JsonProperty("ResultCode") int resultCode,
                                        @JsonProperty("ResultMessage") String resultMessage) {
        super(Searchable.TOPIC, resultCode, resultMessage);
        this.topics = topics;
    }

    public Set<TopicJSON> getTopics() {
        return topics;
    }

    public void setTopics(Set<TopicJSON> topics) {
        this.topics = topics;
    }
}
