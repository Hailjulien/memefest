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
        name = "PostVideo.findByPostId",
        query = "SELECT p FROM PostImageEntity p WHERE p.id.postId = :postId"),
    @NamedQuery(
        name = "PostVideo.findByVidId", 
        query = "SELECT p FROM PostImageEntity p WHERE p.id.vidId = :vidId")
})
@Entity(name = "PostVideoEntity")
@Table(name = "POST_VIDEOS")
public class PostVideo{
  @EmbeddedId
  private PostVideoId id;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Post_Id", referencedColumnName = "Post_Id")
  private Post post;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Vid_Id", referencedColumnName = "Vid_Id")
  private Video video;
  
  public Video getVideo() {
    return this.video;
  }
  
  public void setVideo(Video video) {
    this.video = video;
  }
  
  public void setPost(Post post) {
    this.post = post;
  }
  
  public Post getPost() {
    return this.post;
  }
  
  public void setVid_Id(int vidId) {
    this.id.setVid_Id(vidId);
  }
  
  public void setPost_Id(int postId) {
    this.id.setPost_Id(postId);
  }
  
  public int getPost_Id() {
    return this.id.getPost_Id();
  }
  
  public int getVid_Id() {
    return this.id.getVid_Id();
  }
}