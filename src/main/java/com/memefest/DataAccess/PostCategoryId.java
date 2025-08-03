package com.memefest.DataAccess;

    import jakarta.persistence.Column;
    import jakarta.persistence.Embeddable;

    @Embeddable
    public class PostCategoryId {
    @Column(name = "Cat_Id", nullable = false, updatable = false, insertable = false)
    private int catId;
    
    @Column(name = "Post_Id", nullable = false, updatable = false, insertable = false)
    private int postId;
    
    public int getCat_Id() {
        return this.catId;
    }
    
    public void setCat_Id(int catId) {
        this.catId = catId;
    }
    
    public int getPost_Id() {
        return this.postId;
    }
    
    public void setPost_Id(int postId) {
        this.postId = postId;
    }
    }

