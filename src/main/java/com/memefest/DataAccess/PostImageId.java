package com.memefest.DataAccess;

    import jakarta.persistence.Column;
    import jakarta.persistence.Embeddable;

    @Embeddable
    public class PostImageId {
    @Column(name = "Img_Id", nullable = false, updatable = false, insertable = false)
    private int imgId;
    
    @Column(name = "Post_Id", nullable = false, updatable = false, insertable = false)
    private int postId;
    
    public int getImg_Id() {
        return this.imgId;
    }
    
    public void setImg_Id(int imgId) {
        this.imgId = imgId;
    }
    
    public int getPost_Id() {
        return this.postId;
    }
    
    public void setPost_Id(int postId) {
        this.postId = postId;
    }
    }

