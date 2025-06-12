package com.memefest.DataAccess;

import java.util.Date;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FetchType;
import jakarta.persistence.FieldResult;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;

@NamedNativeQueries({
    @NamedNativeQuery(name = "Post.getPostByComment",
    query = "SELECT TOP(1) P.Post_Id as postId, P.Comment as comment, P.Created as created," 
                   + "P.Upvotes as upvotes, P.downvotes as downvotes, P.UserId as userId FROM POST P "
                + "WHERE P.Comment LIKE CONCAT(CONCAT( '%',?),'%')", resultSetMapping = "PostEntityMapping"
    )
})
@SqlResultSetMappings(
    @SqlResultSetMapping(
        name = "PostEntityMapping",
        entities = {
            @EntityResult(
                entityClass = Post.class,
                fields = {
                    @FieldResult(name = "postId", column = "postId"),
                    @FieldResult(name = "comment", column = "comment"),
                    @FieldResult(name = "created", column = "created"),
                    @FieldResult(name = "upvotes", column = "upvotes"),
                    @FieldResult(name = "downvotes", column = "downvotes"),
                    @FieldResult(name = "userId", column = "userId"),
                }
            )
        }
    )   
)
@NamedQueries({
    @NamedQuery(
        name = "Post.findByUserId",
        query = "SELECT p FROM PostEntity p WHERE p.userId = :userId"
    )
})
@Entity(name = "PostEntity")
@Table(name = "POST")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Post_Id")
    private int postId;

    @Column(name = "Comment")
    private String comment;
    
    @Column(name = "UserId", nullable = false, updatable = false, insertable = false)
    private int userId;

    @Column(name = "Created")
    private Date created;

    @Column(name = "Upvotes")
    private int upvotes;

    @Column(name = "Downvotes")
    private int downvotes;

    
    /* 
    @Column(name = "Video_Id",nullable = true, insertable = true, updatable = true)
    private int videoId;

    @Column(name = "Img_Id", nullable = true, insertable = true, updatable = true)
    private int imageId;
    */

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "post", optional = true)
    private PostReply parent;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "post", optional = true)
    private TopicPost topic;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private User user;   

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "post")
    private Set<PostVideo> videos;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "post")
    private Set<EventPost> eventPosts;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "post")
    private Set<PostImage> images;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "parent")
    private Set<PostReply> postWithReplys;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "post")
    private Set<PostNotification> notifications;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "post")
    private Set<Repost> reposts;

    public int getPost_Id(){
        return postId;
    }

    public void setPost_Id(int postId){
        this.postId = postId;
    }
    
    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }    
    
    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }   

    public Set<PostReply> postReplys(){
        return postWithReplys;
    }

    public void setPostReplys(Set<PostReply> postReplys){
        this.postWithReplys = postReplys;
    }

    public Set<PostNotification> getNotifications(){
        return this.notifications;
    }

    public void setNotifications(Set<PostNotification> notifications) {
        this.notifications = notifications;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public void addReply(PostReply postReply) {
        postWithReplys.add(postReply);
    }

    public void removeReply(PostReply postReply) {
        postWithReplys.remove(postReply);
    }

    public int getReplyCount() {
        return postWithReplys.size();
    } 

    public Set<PostImage> getImages() {
        return images;    
    }

    public Set<PostVideo> getVideos() {
        return videos;
    }

    public void setVideos(Set<PostVideo> videos) {
        this.videos = videos;
    }

    public void setReposts(Set<Repost> reposts){
        this.reposts = reposts;
    }

    public Set<Repost> getReposts(){
        return this.reposts;
    }

    public Set<EventPost> getEventPosts(){
        return this.eventPosts;
    }
}
