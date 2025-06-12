package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@NamedQueries(
    {@NamedQuery(
        name = "EventPost.findByEventId", 
        query = "SELECT u FROM EventPostEntity u WHERE u.eventPostId.eventId = :eventId")
})
@Entity(name = "EventPostEntity")
@Table(name = "Event_POST")
public class EventPost{
  @EmbeddedId
  private EventPostId eventPostId = new EventPostId();

  @ManyToOne
  @JoinColumn(name = "Post_Id", referencedColumnName = "Post_Id")
  private Post post;

  @OneToOne(cascade = {CascadeType.MERGE})
  @JoinColumn(name= "Event_Id", referencedColumnName ="Event_Id")
  private Event event;
  
  public int getPost_Id() {
    return this.eventPostId.getPost_Id();
  }

  public void setPost_Id(int postId){
    this.eventPostId.setPost_Id(postId);
  }
  
  public void setEvent_Id(int imgId) {
    this.eventPostId.setEvent_Id(imgId);
  }

  public void setPost(Post post) {
    this.setPost_Id(post.getPost_Id());
    this.post = post;
  }
  
  public Post getPost() {
    return this.post;
  }

  public Event getEvent(){
    return this.event;
  }

  public void setEvent(Event event){
    this.eventPostId.setEvent_Id(event.getEvent_Id());
    this.event = event;
  }
}
    