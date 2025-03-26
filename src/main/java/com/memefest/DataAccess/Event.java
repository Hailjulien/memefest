package com.memefest.DataAccess;

import java.util.Date;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;


@NamedQueries({
    @NamedQuery(name = "Event.getEventById", query = "SELECT e FROM EventEntity u Where u.eventId  = :eventId")
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "Event.getEventByTitle", 
        query = "SELECT TOP(1) E.Event_Id as eventId, E.Event_Title as eventTitle, E.Date_Posted as created , E.Event_Description "
            +"as description, E.Event_Date as eventDate, E.Event_Pin as eventPin, E.Posted_By as postedBy, E.Event_venue "
            +"as venue FROM TOPIC T WHERE E.Event_Title LIKE CONCAT('%', :title, '%')",
        resultSetMapping = "EventEntityMapping"),
    @NamedNativeQuery(name = "Event.SeaarchEventsByTitle", 
        query = "SELECT E.Event_Id as eventId, E.Event_Title as eventTitle, E.Date_Posted as created , E.Event_Description "
            +"as description, E.Event_Date as eventDate, E.Event_Pin as eventPin, E.Posted_By as postedBy, E.Event_venue "
            +"as venue FROM TOPIC T WHERE E.Event_Title LIKE CONCAT('%', :title, '%')",
        resultSetMapping = "EventEntityMapping")})
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "TopicEntityMapping",
        entities = {@EntityResult(entityClass = Event.class, fields = {
                        @FieldResult(name = "eventId", column = "eventId"),
                        @FieldResult(name = "eventTitle", column = "eventTitle"), 
                        @FieldResult(name = "date", column = "eventDate"), 
                        @FieldResult(name = "datePosted", column = "created"),
                        @FieldResult(name = "eventDesc", column = "description"),
                        @FieldResult(name = "eventPin", column = "eventPin"),
                        @FieldResult(name = "userId", column = "postedBy"),
                        @FieldResult(name = "venue", column = "venue")
                    })}
    )
})
@Entity(name = "EventEntity")
@Table(name = "EVENT_INFO")
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Event_Id")
    private int eventId;

    @Column(name = "Event_Title")
    private String eventTitle;

    @Column(name = "Event_Description")
    private String eventDesc;

    @Column(name = "Event_Date")
    private Date date;

    @Column(name = "Date_Posted")
    private Date datePosted;

    @Column(name = "Event_Pin")
    private String eventPin;

    @Column(name = "Poster_Id")
    private int posterId;

    @Column(name = "Video_Id")
    private int videoId;

    @Column(name = "Posted_By")
    private int userId;

    @Column(name = "Event_Venue")
    private String venue;

    @OneToMany(mappedBy = "event")
    @JoinColumn(name = "Event_Id")
    private Set<EventVideo> videos;

    @OneToMany(mappedBy = "event")
    @JoinColumn(name = "Event_Id")
    private Set<EventImage> images;

    @OneToMany(mappedBy = "event")
    @JoinColumn(name = "Post_Id")
    private Set<EventPost> posts;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    public int getEvent_Id() {
        return this.eventId;
    }

    public void setEvent_Id(int eventId) {
        this.eventId = eventId;
    }

    public String getEvent_Title() {
        return this.eventTitle;
    }

    public void setEvent_Title(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEvent_Description() {
        return this.eventDesc;
    }

    public void setEvent_Description(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public void setEvent_Pin(String eventPin){
        this.eventPin = eventPin;
    }

    public String getEvent_Pin(){
        return this.eventPin;
    }

    public Date getEvent_Date(){
        return this.date;
    }

    public void setEvent_Date(Date date){
        this.date = date;
    }

    public Date getDate_Posted(){
        return this.datePosted;
    }

    public void setDate_Posted(Date datePosted){
        this.datePosted = datePosted;
    }

    public int getUserId(){
        return this.userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public String getEvent_Venue() {
        return this.venue;
    }

    public void setEvent_Venue(String venue) {
        this.venue = venue;
    }

    public Set<EventVideo> getVideos() {
        return this.videos;
    }

    public void setVideos(Set<EventVideo> videos) {
        this.videos = videos;
    }

    public Set<EventImage> getImages() {
        return this.images;
    }

    public void setImages(Set<EventImage> images) {
        this.images = images;
    }

    public User getPostedBy(){
        return this.user;
    }

    public void setPostedBy(User user){
        this.user = user;
    }

    public Set<EventPost> getPosts() {
        return this.posts;
    }

    public void setPosts(Set<EventPost> posts) {
        this.posts = posts;
    }
}
