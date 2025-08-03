package com.memefest.DataAccess;

    import jakarta.persistence.Column;
    import jakarta.persistence.Embeddable;

    @Embeddable
    public class EventCategoryId {
    @Column(name = "Cat_Id", nullable = false, updatable = false, insertable = false)
    private int catId;
    
    @Column(name = "Event_Id", nullable = false, updatable = false, insertable = false)
    private int eventId;
    
    public int getCat_Id() {
        return this.catId;
    }
    
    public void setCat_Id(int catId) {
        this.catId = catId;
    }
    
    public int getEvent_Id() {
        return this.eventId;
    }
    
    public void setEvent_Id(int postId) {
        this.eventId = postId;
    }
    }

