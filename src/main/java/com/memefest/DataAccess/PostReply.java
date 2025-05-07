package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({@NamedQuery(name = "PostReplyEntity.getRepliesOfPostId", 
  query = "SELECT pr FROM PostReplyEntity pr WHERE pr.postId = :postId")})
@Entity(name = "PostReplyEntity")
@Table(name = "REPLY")
public class PostReply extends Post {
  @Column(name = "Post_Info", updatable = false, nullable = false, insertable = false)
  private int childPostId;
  
  @ManyToOne(cascade = {CascadeType.MERGE})
  @JoinColumn(name = "Post_Info", referencedColumnName = "Post_Id")
  private Post post;
  
  public Post getPost() {
    return this.post;
  }
  
  public void setPost(Post post) {
    this.post = post;
  }
  
  public int getPost_Info() {
    return this.childPostId;
  }
  
  public void setPost_Info(int childPostId) {
    this.childPostId = childPostId;
  }
}
