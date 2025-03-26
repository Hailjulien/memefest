package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;

@JsonRootName("GetTopicPost")
public class GetTopicPostJSON extends GetPostJSON{
    
    @JsonProperty("TopicPosts")
    private Set<TopicPostJSON> topicPosts;

    @JsonCreator
    public GetTopicPostJSON(@JsonProperty("TopicPosts") Set<TopicPostJSON> topicPosts) {
        super(topicPosts.stream().map(
                post -> (PostJSON) post
            ).collect(Collectors.toSet()));
        this.topicPosts = topicPosts;
    }

    public Set<TopicPostJSON> getTopicPosts() {
        return topicPosts;
    }

    public void setTopicPosts(Set<TopicPostJSON> posts){
        this.topicPosts = posts;
        super.setPosts(posts.stream().map(
                post -> (PostJSON) post
        ).collect(Collectors.toSet()));
    }
}
