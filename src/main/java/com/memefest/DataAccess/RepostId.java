package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RepostId {
    @Column(name = "Post_Id", nullable = false, insertable = false, updatable= false)
    private int postId;

    @Column(name = "UserId", nullable = false, insertable = false, updatable= false)
    private int userId;

    public int getPost_Id() {
        return postId;
    }

    public void setPost_Id(int postId){
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }
}
