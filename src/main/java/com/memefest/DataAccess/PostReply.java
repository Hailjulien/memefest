package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
   
@NamedQueries({@NamedQuery(name = "PostReplyEntity.getRepliesOfPostId", 
  query = "SELECT pr FROM PostReplyEntity pr WHERE pr.postReplyId.parentId = :postId")})
@Entity(name = "PostReplyEntity")
@Table(name = "REPLY")
public class PostReply{

  @EmbeddedId
  private PostReplyId postReplyId = new PostReplyId();

  @Column(name = "Post_Info", updatable = false, nullable = false, insertable = false)
  private int parentPostId;
  
  @OneToOne(cascade = {CascadeType.MERGE})
  @JoinColumn(name = "Post_Id", referencedColumnName = "Post_Id")
  private Post post;

  @ManyToOne
  @JoinColumn(name = "Post_Info", referencedColumnName = "Post_Id")
  private Post parent;

  
  public Post getPost() {
    return this.post;
  }

  public void setParent(Post parent){
    this.postReplyId.setPost_Info(parentPostId);
    this.parent = parent;
  }

  public Post getParent(){
    return this.parent;
  }
  
  public void setPost(Post post) {
    this.postReplyId.setPost_Id(post.getPost_Id());
    this.post = post;
  }
  
  public int getPost_Info() {
    return this.parentPostId;
  }
  
  public void setPost_Info(int parentPostId) {
    this.postReplyId.setPost_Info(parentPostId);
  }

  public int getPost_Id(){
    return this.postReplyId.getPost_Id();
  }

  public void setPost_Id(int postId){
    this.postReplyId.setPost_Id(postId);
  }
}
