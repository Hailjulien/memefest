package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(name = "TopicPostNotification.getTopicNotificationByTopicId",
        query = "SELECT tPN FROM TopicPostNotificationEntity tPN WHERE tPN.topicId = :topicId"),
    @NamedQuery(name = "TopicPostNotification.getTopicNotificationByPostId",
        query = "SELECT tPN FROM TopicPostNotificationEntity tPN WHERE tPN.id.postId = :postId"),
    @NamedQuery(name = "TopicPostNotification.getTopicNotificationByUser",
        query =  "SELECT tPN FROM TopicPostNotificationEntity tPN WHERE tPN.id.recipientId = :userId" )
})
@Entity(name = "TopicPostNotificationEntity")
@Table(name = "TOPIC_POST_NOTIFICATION")
public class TopicPostNotification extends PostNotification {
    
    @Column(name = "Topic_Id", nullable = false, insertable = false, updatable = false)
    private int topicId;

    @ManyToOne
    @JoinColumn(name = "Topic_Id", referencedColumnName = "Topic_Id")
    private Topic topic;

    public void setTopic(Topic topic){
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getTopicId() {
        return topicId;
    }

}
