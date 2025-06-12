package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "RepostEntity")
@Table(name = "REPOST")
public class Repost {
    @Id
    @EmbeddedId
    private RepostId repostId = new RepostId();

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "Post_Id")
    private Post post;

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost_Id(int postId) {
        this.repostId.setPost_Id(postId);
    }

    public int getPost_Id(){
        return this.repostId.getPost_Id();
    }

    public int getUserId() {
        return this.repostId.getUserId();
    }

    public void setUserId(int userId){
        this.repostId.setUserId(userId);
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }
    public void setPost(Post post){
        this.post = post;
    }

}
