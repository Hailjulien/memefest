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
        name = "PostVideo.findByPostId",
        query = "SELECT p FROM PostVideoEntity p WHERE p.postId = :postId"),
    @NamedQuery(
        name = "PostVideo.findByVidId", 
        query = "SELECT p FROM PostVideoEntity p WHERE p.vidId = :vidId")
})
@Entity(name = "PostVideoEntity")
@Table(name = "POST_VIDEOS")
public class PostVideo extends Video{
  
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