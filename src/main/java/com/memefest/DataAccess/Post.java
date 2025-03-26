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
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;

@NamedNativeQueries({
    @NamedNativeQuery(name = "Post.getPostByComment",
    query = "SELECT TOP(1) * FROM POST P.Post_Id as postId, P.Comment as comment, P.Created as created," 
                   + "P.Upvotes as upvotes, P.downvotes as downvotes "
                + "WHERE P.Comment LIKE CONCAT('%', :comment, '%')", resultSetMapping = "PostEntityMapping"
    )
})
@SqlResultSetMappings(
    @SqlResultSetMapping(
        name = "PostEntityMapping",
        entities = {
            @EntityResult(
                entityClass = MainCategory.class,
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
        name = "Post.findByTopicId",
        query = "SELECT p FROM PostEntity p WHERE p.topic.topicId = :topicId"
    ),
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

    @Column(name = "Topic_Id", nullable = false, updatable = false, insertable = false)
    private int topicId;

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

    @Column(name = "Event_Id")
    private int eventId;

    @Column(name = "Video_Id")
    private int videoId;

    @Column(name = "Image_Id")
    private int imageId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "UserId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Event_Id", referencedColumnName = "Event_Id")
    private Event event;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Video_Id", referencedColumnName = "Video_Id")
    private Set<Video> videos;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Image_Id", referencedColumnName = "Video_Id")
    private Set<Image> images;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "post")
    private Set<PostReply> postWithReplys;



    public int getPost_Id(){
        return postId;
    }

    public void setPost_Id(int postId){
        this.postId = postId;
    }

    public int getTopic_Id(){
        return topicId;
    }

    public void setTopic_Id(int topicId){
        this.topicId = topicId;
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

    public Event getEvent() {
        return event;    
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }


}
