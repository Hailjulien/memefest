package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;

@JsonRootName("EditResultTopicPost")
public class EditResultTopicPostJSON extends EditResultJSON {
    
    @JsonProperty("TopicPosts")
    private Set<TopicPostJSON> topicPosts;

    @JsonCreator
    public EditResultTopicPostJSON(
            @JsonProperty("ResultCode") int resultCode,
            @JsonProperty("ResultMessage") String resultMessage,
            @JsonProperty("TopicPosts") Set<TopicPostJSON>topicPosts){
        super(Editable.POST, resultCode, resultMessage);
        this.topicPosts = topicPosts;
    }

    public void setTopicPosts(Set<TopicPostJSON> topicPosts){
        this.topicPosts = topicPosts;
    }

    public Set<TopicPostJSON> getTopicPosts(){
        return this.topicPosts;
    }
}
