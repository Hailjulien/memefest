package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@NamedQueries(
    {@NamedQuery(
        name = "TopicPost.findByTopicId", 
        query = "SELECT u FROM TopicPostEntity u WHERE u.topicId = :topicId"), 
})

@Entity(name = "TopicPostEntity")
@Table(name = "TOPIC_POST")
public class TopicPost  extends Post{

    @Column(name = "Topic_Id", nullable = false, updatable = false, insertable = false)
    private int topicId; 

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Topic_Id", referencedColumnName = "Topic_Id")
    private Topic topic;
        
    public Topic getTopic() {
        return this.topic;
    }
        
    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public int getTopic_Id(){
        return this.topicId;
    }

    public void setTopic_Id(int topicId){
        this.topicId = topicId;
    }
}
    