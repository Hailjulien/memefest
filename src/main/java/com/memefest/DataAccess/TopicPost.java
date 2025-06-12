package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@NamedQueries(
    {@NamedQuery(
        name = "TopicPost.findByTopicId", 
        query = "SELECT u FROM TopicPostEntity u WHERE u.topicPostId.topicId = :topicId"), 
  @NamedQuery(
        name = "TopicPost.findByPostId", 
        query = "SELECT u FROM TopicPostEntity u WHERE u.topicPostId.postId = :topicId") 
})
@Entity(name = "TopicPostEntity")
@Table(name = "TOPIC_POST")
public class TopicPost{

    @EmbeddedId
    TopicPostId topicPostId = new TopicPostId();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Topic_Id", referencedColumnName = "Topic_Id")
    private Topic topic;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Post_Id", referencedColumnName = "Post_Id")
    private Post post;
        
    public Topic getTopic() {
        return this.topic;
    }
        
    public void setTopic(Topic topic) {
        this.topic = topic;
        this.topicPostId.setTopic_Id(topic.getTopic_Id());
    }

    public int getTopic_Id(){
        return this.topicPostId.getTopic_Id();
    }

    public void setTopic_Id(int topicId){
        this.topicPostId.setTopic_Id(topicId);
    }

    public Post getPost(){
        return this.post;
    }

    public void setPost(Post post){
        this.post = post;
    }

    public int getPost_Id(){
        return topicPostId.getPost_Id();
    }

    public void setPost_Id(int postId){
        this.topicPostId.setPost_Id(postId);
    }
}
    