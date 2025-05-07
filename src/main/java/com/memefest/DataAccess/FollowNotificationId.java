package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FollowNotificationId {
    
    @Column(name = "UserId", nullable = false, insertable = false, updatable= false)
    private int userId;

    @Column(name = "Follower_Id", nullable = false, insertable = false, updatable= false)
    private int followerId;

    public int getUserId(){
        return userId;
    }

    public int getFollower_Id(){
        return followerId;
    }
    
    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setFollower_Id(int followerId){
        this.followerId = followerId;
    }
}
