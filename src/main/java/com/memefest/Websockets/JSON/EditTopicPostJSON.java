package com.memefest.Websockets.JSON;


import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;

@JsonRootName("EditTopicPosts")
public class EditTopicPostJSON extends EditPostJSON{
    
    @JsonProperty("TopicPosts")
    private Set<TopicPostJSON> topicPosts;  

    @JsonCreator
    public EditTopicPostJSON(@JsonProperty("TopicPosts") Set<TopicPostJSON> topicPosts){
        super(topicPosts.stream().map(candidate -> {
            return (PostJSON)candidate;
        }).collect(Collectors.toSet()));
        this.topicPosts = topicPosts;
    }

    public Set<TopicPostJSON> getTopicPosts(){
        return this.topicPosts;
    }

    public void setTopicPosts(Set<TopicPostJSON> topicPosts){
        this.topicPosts = topicPosts;
    }   

}
