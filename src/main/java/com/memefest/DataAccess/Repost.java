package com.memefest.DataAccess;

import jakarta.persistence.Column;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Repost_Id")
    private int repostId;

    @Column(name = "Post_Id", nullable = false, insertable = false, updatable= false)
    private int postId;

    @Column(name = "UserId", nullable = false, insertable = false, updatable= false)
    private int userId;

    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "Post_Id")
    private Post post;

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost_Id(Post post) {
        this.post = post;
    }

    public int getRepostId() {
        return repostId;
    }

    public int getPost_Id() {
        return postId;
    }

    public int getUserId() {
        return userId;
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
