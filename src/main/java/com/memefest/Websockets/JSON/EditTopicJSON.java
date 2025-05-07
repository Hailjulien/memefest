package com.memefest.Websockets.JSON;

import java.util.Set;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditTopic")
public class EditTopicJSON extends EditJSON{
    
    @JsonProperty("Topics")
    private Set<TopicJSON> topics; 

    @JsonCreator
    public EditTopicJSON(@JsonProperty("Topics") Set<TopicJSON> topics) {
        super(Editable.TOPIC);
        this.topics = topics;
    }

    public Set<TopicJSON> getTopics(){
        return this.topics;
    }

    public void setTopics(Set<TopicJSON> topics){
        this.topics = topics;
    }
}