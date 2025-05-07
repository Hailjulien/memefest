package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;

@JsonRootName("GetTopicResult")
public class GetResultTopicPostJSON extends GetResultPostJSON{
    
    @JsonProperty("TopicPosts")
    private Set<TopicPostJSON> topicPosts;

    public GetResultTopicPostJSON(@JsonProperty("ResultCode") int resultCode, @JsonProperty("ResultMessage") String resultMessage,
                                    @JsonProperty("ResultPosts") Set<TopicPostJSON> topicPosts){
        super(resultCode, resultMessage,topicPosts.stream().map(topicInst ->{
                                            return (PostJSON)topicInst;
                                        }).collect(Collectors.toSet()));
        this.topicPosts = topicPosts;
    }
    public void setTopicPosts(Set<TopicPostJSON> topicPosts) {
        this.topicPosts = topicPosts;
    }
    
    public Set<TopicPostJSON> getTopicPosts() {
        return topicPosts;
    }


}
