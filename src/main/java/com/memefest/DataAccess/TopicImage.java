package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
  @NamedQuery(
    name = "TopicImage.getByTopicId", 
    query = "SELECT tc FROM TopicImageEntity tc WHERE tc.topicId = :topicId"),
  @NamedQuery(
    name = "TopicImage.getByImageId",
    query = "SELECT tc FROM TopicImageEntity tc WHERE tc.imageId = :imageId")
})
@Entity(name = "TopicImageEntity")
@Table(name = "TOPIC_IMAGE")
public class TopicImage extends Image{
  
  @Column(name = "Topic_Id", nullable = false, updatable = false, insertable = false)
  private int topicId; 

  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Topic_Id", referencedColumnName = "Topic_Id")
  private Topic topic;
  
  public int getTopic_Id() {
    return this.topicId;
  }
  
  public void setTopic_Id(int topicId) {
    this.topicId = topicId;
  }

  public void setTopic(Topic topic) {
    this.topic = topic;
  }
  
  public Topic getTopic() {
    return this.topic;
  }
}
