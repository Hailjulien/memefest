package com.memefest.Websockets.JSON;



import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.TopicJSON;

@JsonRootName("EditResultScheduledTopic")
public class EditResultScheduledTopicJSON extends EditResultJSON{
    
    @JsonProperty("EventTimes")
    @JsonSerialize(keyAs = TopicJSON.class)
    //JsonSerialize(keyUsing = MapSerializer.class)
    private Map<TopicJSON, LocalDateTime> topicTimes; 

    @JsonCreator
    public EditResultScheduledTopicJSON(@JsonProperty("ResultCode") int resultCode, 
                                            @JsonProperty("ResultMessage") String resultMessage,
                                                @JsonProperty("TopicTimes")Map<TopicJSON,LocalDateTime> topicTimes){
        super(Editable.TOPIC, resultCode, resultMessage);
        this.topicTimes = topicTimes;
    }

    public void setTopicTimes(Map<TopicJSON, LocalDateTime> topicTimes){
        this.topicTimes = topicTimes;
    }

    public Map<TopicJSON, LocalDateTime> getTopicTimes(){
        return this.topicTimes;
    }
}
