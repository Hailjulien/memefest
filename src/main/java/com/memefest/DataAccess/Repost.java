package com.memefest.DataAccess;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity(name = "RepostEntity")
@Table(name = "REPOST")
public class Repost {
    
    @EmbeddedId
    private RepostId repostId = new RepostId();

    @ManyToOne
    //@JoinColumn(referencedColumnName ="UserId", name = "UserId")
    //@MapsId("userId")
    @JoinColumn(name= "UserId", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne
    //@MapsId("postId")
    @JoinColumn(name ="Post_Id", nullable =  false, insertable = false, updatable = false)
    //@JoinColumn(referencedColumnName = "Post_Id", name ="Post_Id")
    private Post post;

    public void setUser(User user) {
        this.setUserId(user.getUserId());
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
        this.setPost_Id(post.getPost_Id());
        this.post = post;
    }

}
