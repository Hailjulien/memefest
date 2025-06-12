package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@NamedQueries({
  @NamedQuery(
    name = "TopicImage.getByTopicId", 
    query = "SELECT tc FROM TopicImageEntity tc WHERE tc.topicImgId.topicId = :topicId"),
  @NamedQuery(
    name = "TopicImage.getByImageId",
    query = "SELECT tc FROM TopicImageEntity tc WHERE tc.topicImgId.imgId = :imageId")
})
@Entity(name = "TopicImageEntity")
@Table(name = "TOPIC_IMAGES")
public class TopicImage{
  
  @EmbeddedId
  private TopicImageId topicImgId = new TopicImageId();

  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Topic_Id", referencedColumnName = "Topic_Id")
  private Topic topic;

  @OneToOne(cascade = {CascadeType.MERGE})
  @JoinColumn(name= "Poster_Id", referencedColumnName ="Img_Id")
  private Image image;
  
  public int getTopic_Id() {
    return this.topicImgId.getTopic_Id();
  }

  public void setTopic_Id(int topicId){
    this.topicImgId.setTopic_Id(topicId);
  }
  
  public void setPoster_Id(int imgId) {
    this.topicImgId.setPoster_Id(imgId);
  }

  public void setTopic(Topic topic) {
    this.topic = topic;
    this.setTopic_Id(topic.getTopic_Id());
  }
  
  public Topic getTopic() {
    return this.topic;
  }

  public Image getPoster(){
    return this.getPoster();
  }

  public void setPoster(Image image){
    this.image = image;
    this.topicImgId.setPoster_Id(image.getImg_Id());
  }
}
