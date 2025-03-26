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
        name = "PostImage.findByPostId",
        query = "SELECT p FROM PostImageEntity p WHERE p.id.postId = :postId"),
    @NamedQuery(
        name = "PostImage.findByImgId", 
        query = "SELECT p FROM PostImageEntity p WHERE p.id.imageId = :imageId")
})
@Entity(name = "PostImageEntity")
@Table(name = "POST_IMAGES")
public class PostImage{
  @EmbeddedId
  private PostImageId id;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Post_Id", referencedColumnName = "Post_Id")
  private Post post;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Img_Id", referencedColumnName = "Img_Id")
  private Image image;
  
  public Image getImage() {
    return this.image; 
  }
  
  public void setImage(Image image) {
    this.image = image;
  }
  
  public void setPost(Post post) {
    this.post = post;
  }
  
  public Post getPost() {
    return this.post;
  }
  
  public void setImg_Id(int imageId) {
    this.id.setImg_Id(imageId);
  }
  
  public void setPost_Id(int postId) {
    this.id.setPost_Id(postId);
  }
  
  public int getPost_Id() {
    return this.id.getPost_Id();
  }
  
  public int getImg_Id() {
    return this.id.getImg_Id();
  }
}