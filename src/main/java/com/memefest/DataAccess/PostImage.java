package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
    @NamedQuery(
        name = "PostImage.findByPostId",
        query = "SELECT p FROM PostImageEntity p WHERE p.postId = :postId"),
    @NamedQuery(
        name = "PostImage.findByImgId", 
        query = "SELECT p FROM PostImageEntity p WHERE p.imageId = :imageId")
})
@Entity(name = "PostImageEntity")
@Table(name = "POST_IMAGES")
public class PostImage extends Image{
  
  @Column(name = "Post_Id", nullable = false, updatable = false, insertable = false)
  private int postId; 

  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Post_Id", referencedColumnName = "Post_Id")
  private Post post;
  
  public void setPost(Post post) {
    this.post = post;
  }
  
  public Post getPost() {
    return this.post;
  }

  public void setPost_Id(int postId) {
    this.postId = postId;
  }
  
  public int getPost_Id() {
    return this.postId;
  }
}