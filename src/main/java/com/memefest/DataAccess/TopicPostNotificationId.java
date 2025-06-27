package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TopicPostNotificationId extends PostNotificationId{
    
    @Column(name = "TopicId")
    private int topicId;
            
    @Column(name = "Post_Id", nullable = false, insertable = false, updatable = false)
    private int postId;

    @Column(name = "UserId", nullable = false, insertable = false, updatable = false)
    private int recipientId;


    public int getTopic_Id(){
        return this.topicId;
    }

    public void setTopic_Id(int topicId){
        this.topicId = topicId;
    }

    public int getPost_Id(){
        return postId;
    }

    public void setPost_Id(int postId){
        this.postId = postId;
    }

    public int getUserId(){
        return this.postId;
    }

    public void setUserId(int recipientId){
        this.recipientId = recipientId;
    }
}
