    package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FetchType;
import jakarta.persistence.FieldResult;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;

//use @ElementCollection , @Embeddable and @CollectionTable  instead?
@NamedNativeQueries({
    @NamedNativeQuery(name = "Video.getVideoByTitle",
    query = "SELECT TOP(1) * FROM VIDEOS V " 
                + "WHERE V.Vid_Title LIKE CONCAT('%', :title, '%')", resultSetMapping = "VideoEntityMapping"
    )
})
@SqlResultSetMappings(
    @SqlResultSetMapping(
        name = "VideoEntityMapping",
        entities = {
            @EntityResult(
                entityClass = TopicPost.class,
                fields = {
                    @FieldResult(name = "vidId", column = "Vid_Id"),
                    @FieldResult(name = "vidTitle", column = "Vid_Title"),
                    @FieldResult(name = "vidPath", column = "Vid_Path")
                }
            )
        }
    )   
) 
@Entity(name = "VideoEntity")
@Table(name = "VIDEOS")
public class Video {
    
    @Id
    @Column(name = "Vid_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vidId;
    /* 
    @Column(name = "Vid_Description")
    private String vidDescription;
    */

    
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "UserId")
    private User user;

    /* 
    @OneToOne(fetch =FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "video", optional =  true)
    private TopicVideo topic;

    @OneToOne(fetch =FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "video", optional =  true)
    private PostVideo post;
    */
    @OneToOne(fetch =FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "video", optional =  true)
    private EventVideo event;

    
    @Column(name = "Vid_Path")
    private String vidUrl;

    public int getVid_Id() {
        return this.vidId;
    }

    public void setVid_Id(int vidId) {
        this.vidId = vidId;
    }



    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public String getVid_Path() {
        return this.vidUrl;
    }

    public void setVid_Path(String vidUrl) {
        this.vidUrl = vidUrl;
    }
}
