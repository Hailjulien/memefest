package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
  @NamedQuery(
    name = "TopicCategory.getByTopicId", 
    query = "SELECT tc FROM TopicCategoryEntity tc WHERE tc.topic.topicId = :topicId"),
  @NamedQuery(
    name = "TopicCategory.getByCategoryId",
    query = "SELECT tc FROM TopicCategoryEntity tc WHERE tc.category.categoryId = :categoryId")
})
@Entity(name = "TopicCategoryEntity")
@Table(name = "TOPIC_CATEGORY")
public class TopicCategory {
  @EmbeddedId
  private TopicCategoryId topicId;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Topic_Id", referencedColumnName = "Topic_Id")
  private Topic topic;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Cat_Id", referencedColumnName = "Cat_Id")
  private Category category;
  
  public int getTopic_Id() {
    return this.topicId.getTopic_Id();
  }
  
  public void setTopic_Id(int topicId) {
    this.topicId.setTopic_Id(topicId);
  }
  
  public void setCat_Id(int categoryId) {
    this.topicId.setCat_Id(categoryId);
  }
  
  public void setCategory(Category category) {
    this.category = category;
  }
  
  public Category getCategory() {
    return this.category;
  }
  
  public void setTopic(Topic topic) {
    this.topic = topic;
  }
  
  public Topic getTopic() {
    return this.topic;
  }
}
