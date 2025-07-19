package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.TopicJSON;

@JsonRootName("SearchTopic")
public class SearchTopicJSON extends SearchJSON{
    
    @JsonProperty("Topic")
    private TopicJSON topic;

    @JsonCreator
    public SearchTopicJSON(@JsonProperty("Topic") TopicJSON topic){
        super(Searchable.TOPIC);
        this.topic = topic;
    }
    
    public TopicJSON getTopic(){
        return topic;    
    }

    public void setTopics(TopicJSON topic){
        this.topic = topic;
    }
}
