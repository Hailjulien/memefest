package com.memefest.DataAccess;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(
        name = "PostNotification.getByUserId",
        query = "SELECT pn FROM PostNotificationEntity pn WHERE pn.id.recipientId = :userId"
    ),
    @NamedQuery(
        name = "PostNotification.getByPostId",
        query = "SELECT pn FROM PostNotificationEntity pn WHERE pn.id.postId = :postId"
    )
})
@Entity(name = "PostNotificationEntity")
@Table(name = "POST_NOTIFICATION")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PostNotification {

    @EmbeddedId
    private PostNotificationId id;

    @ManyToOne
    @JoinColumn(name = "Post_Id", referencedColumnName = "Post_Id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private User user;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost_Id(int postId) {
        this.id.setPost_Id(postId);
    }

    public int getUserId() {
        return this.id.getUserId();
    }

    public int getPost_Id(){
        return this.id.getPost_Id();
    }

    public void setUserId(int userId) {
        this.id.setUserId(userId);
    }

}
