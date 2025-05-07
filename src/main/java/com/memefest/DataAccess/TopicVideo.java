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
    name = "TopicVideo.getByTopicId", 
    query = "SELECT tc FROM TopicVideoEntity tc WHERE tc.topicId = :topicId"),
  @NamedQuery(
    name = "TopicVideo.getByVideoId",
    query = "SELECT tc FROM TopicVideoEntity tc WHERE tc.vidId = :videoId")
})
@Entity(name = "TopicVideoEntity")
@Table(name = "TOPIC_VIDEO")
public class TopicVideo  extends Video{    

  @Column(name = "Topic_Id", nullable = false, insertable = false, updatable = false)
  private int topicId;

  @ManyToOne
  @JoinColumn(name = "Topic_Id", referencedColumnName = "Topic_Id")
  private Topic topic;

  public void setTopic(Topic topic){
      this.topic = topic;
  }

  public Topic getTopic() {
      return topic;
  }

  public void setTopicId(int topicId) {
      this.topicId = topicId;
  }

  public int getTopicId() {
      return topicId;
  }
}


