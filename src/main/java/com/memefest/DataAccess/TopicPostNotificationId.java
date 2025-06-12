package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TopicPostNotificationId extends PostNotificationId{
    
    @Column(name = "TopicId", nullable = false, insertable = false, updatable = false)
    private int topicId;

    public int getTopic_Id(){
        return this.topicId;
    }

    public void setTopic_Id(int topicId){
        this.topicId = topicId;
    }
}
