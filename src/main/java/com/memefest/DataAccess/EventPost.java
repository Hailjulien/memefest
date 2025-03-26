package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;

//maybe have the class extend Post with a table inheritance strategy as an alternative to linking using primary keys .. much cleaner code
@NamedQueries(
    {@NamedQuery(
        name = "EventPost.findByPostId", 
        query = "SELECT u FROM EventPostEntity u WHERE u.id.postId = :postId"), 
    @NamedQuery(
        name = "EventPost.findByEventId", 
        query = "SELECT e FROM EventPostEntity e WHERE e.id.eventId = :eventId")
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "EventPost.getPostByComment",
    query = "SELECT TOP(1) * FROM POST P JOIN EVENT_POST EP  ON P.Post_Id = EP.Post_Id  P.Post_Id as postId, P.Comment as comment, P.Created as created," 
                   + "P.Upvotes as upvotes, P.downvotes as downvotes "
                + "WHERE P.Comment LIKE CONCAT('%', :comment, '%')", resultSetMapping = "PostEventEntityMapping"
    )
})
@SqlResultSetMappings(
    @SqlResultSetMapping(
        name = "PostEventEntityMapping",
        entities = {
            @EntityResult(
                entityClass = EventPost.class,
                fields = {
                    @FieldResult(name = "postId", column = "Post_Id"),
                    @FieldResult(name = "eventId", column = "Event_Id")
                }
            )
        }
    )   
)
@Entity(name = "EventPostEntity")
@Table(name = "EVENT_POST")
public class EventPost{
    
  @EmbeddedId
  private EventPostId id;
    
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Event_Id", referencedColumnName = "Event_Id")
  private Event event;
    
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Post_Id", referencedColumnName = "Img_Id")
  private Post post;
    
  public Event getEvent() {
    return this.event;
  }
    
  public void setEvent(Event event) {
    this.event = event;
  }
    
  public void setPost(Post post) {
    this.post = post;
  }
    
  public Post getPost() {
    return this.post;
  }
    
  public void setEvent_Id(int eventId) {
    this.id.setEvent_Id(eventId);
  }
    
  public void setPost_Id(int userId) {
    this.id.setPost_Id(userId);
  }
    
  public int getPost_Id() {
    return this.id.getPost_Id();
  }
    
  public int getEvent_Id() {
    return this.id.getEvent_Id();
  }
  }
