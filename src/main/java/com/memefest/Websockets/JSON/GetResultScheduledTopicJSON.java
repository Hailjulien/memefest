package com.memefest.Websockets.JSON;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.memefest.DataAccess.JSON.TopicJSON;

public class GetResultScheduledTopicJSON extends GetResultJSON {
    
    @JsonProperty("TopicTimes")
    @JsonSerialize(keyAs = TopicJSON.class)
    //JsonSerialize(keyUsing = MapSerializer.class)
    private Map<TopicJSON, LocalDateTime> topicTimes; 

    public GetResultScheduledTopicJSON(@JsonProperty("ResultCode") int resultCode, 
                                        @JsonProperty("ResultMessage") String resultMessage,
                                            @JsonProperty("TopicTimes") Map<TopicJSON,LocalDateTime> topicTimes){
        super(Getable.TOPIC, resultCode, resultMessage);
        this.topicTimes = topicTimes;
    }

    public Map<TopicJSON, LocalDateTime> getTopicTimes(){
        return this.topicTimes;
    }

    public void setTopicTimes(Map<TopicJSON, LocalDateTime> topicTimes){
        this.topicTimes = topicTimes;
    }
}
