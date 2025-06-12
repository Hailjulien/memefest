package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.TopicJSON;

@JsonRootName("EditScheduledTopic")
public class GetScheduledTopicJSON  extends GetJSON{
    
    @JsonProperty("Topics")
    //@JsonSerialize(keyAs = TopicJSON.class)
    //JsonSerialize(keyUsing = MapSerializer.class)
    private Set<TopicJSON> topics; 

    public GetScheduledTopicJSON(@JsonProperty("Topics")Set<TopicJSON> topics){
        super(Getable.TOPIC);
        this.topics = topics;
    }

    public void setTopics(Set<TopicJSON> topics){
        this.topics = topics;
    }

    public Set<TopicJSON> getTopics(){
        return this.topics;
    }
}
