package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;

@NamedQueries(
    {@NamedQuery(
        name = "TopicPost.findByTopicId", 
        query = "SELECT u FROM TopicFollowerEntity u WHERE u.id.topicId = :topicId"), 
    @NamedQuery(
        name = "TopicPost.findById", 
        query = "SELECT e FROM TopicPostEntity e WHERE e.id.postId = :postId AND e.id.topicId = :topicId"),
    @NamedQuery(
        name = "TopicPost.getByPostComment", 
        query = "SELECT t FROM TopicPostEntity t JOIN FETCH t.post WHERE t.post.comment LIKE :comment"
    )
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "TopicPost.getPostByComment",
    query = "SELECT TOP(1) * FROM POST P JOIN TOPIC_POST TP P.Post_Id as postId, P.Comment as comment, P.Created as created," 
                   + "P.Upvotes as upvotes, P.downvotes as downvotes "
                + "WHERE P.Post_Id = TP.Post_Id AND P.Comment LIKE CONCAT('%', :comment, '%')", resultSetMapping = "PostTopicEntityMapping"
    )
})
@SqlResultSetMappings(
    @SqlResultSetMapping(
        name = "PostTopicEntityMapping",
        entities = {
            @EntityResult(
                entityClass = TopicPost.class,
                fields = {
                    @FieldResult(name = "postId", column = "Tost_Id"),
                    @FieldResult(name = "topicId", column = "Topic_Id")
                }
            )
        }
    )   
)
@Entity(name = "TopicPostEntity")
@Table(name = "TOPIC_POST")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TopicPost {
  
    @EmbeddedId
    private TopicPostId id;
        
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "Topic_Id", referencedColumnName = "Event_Id")
    private Topic topic;
        
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "Post_Id", referencedColumnName = "Img_Id")
    private Post post;
        
    public Topic getTopic() {
        return this.topic;
    }
        
    public void setTopic(Topic topic) {
        this.topic = topic;
    }
        
    public void setPost(Post post) {
        this.post = post;
    }
        
    public Post getPost() {
        return this.post;
    }
        
    public void setTopic_Id(int topicId) {
        this.id.setTopic_Id(topicId);
    }
        
    public void setPost_Id(int userId) {
        this.id.setPost_Id(userId);
    }
        
    public int getPost_Id() {
        return this.id.getPost_Id();
    }
        
    public int getTopic_Id() {
        return this.id.getTopic_Id();
    }
}
    