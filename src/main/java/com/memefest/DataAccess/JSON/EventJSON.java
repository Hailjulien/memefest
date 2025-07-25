package com.memefest.DataAccess.JSON;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.memefest.DataAccess.JSON.Deserialize.CustomLocalDateTimeDeserializer;
import com.memefest.DataAccess.JSON.Serialize.CustomLocalDateTimeSerializer;

@JsonRootName("Event")
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "EventID")
@JsonFilter("EventPublicView")
public class EventJSON implements Serializable{
    
    @JsonProperty("EventID")
    private int eventID;

    @JsonProperty("EventTitle")
    private String eventTitle;

    @JsonProperty("EventDescription")
    private String eventDescription; 

    @JsonProperty("EventPin")
    private String eventPin;

    @JsonProperty("EventVenue")
    private String eventVenue;

    @JsonProperty("EventDate")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime eventDate;

    @JsonProperty("Posters")
    private Set<ImageJSON> posters;

    @JsonProperty("Clips")
    private Set<VideoJSON> clips;

    @JsonProperty("Posts")
    private Set<EventPostJSON> posts;

    @JsonProperty("PostedBy")
    private UserJSON user;

    @JsonProperty("DatePosted")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    private LocalDateTime datePosted;

    @JsonProperty("Categories")
    private Set<CategoryJSON> categories;

    @JsonProperty("CanceledCategories")
    private Set<CategoryJSON> canceledCats;

    @JsonProperty("CanceledImages")
    private Set<ImageJSON> canceledImages;

    @JsonProperty("CanceledClips")
    private Set<VideoJSON> canceledClips;

    @JsonProperty("IsCanceled")
    private boolean isCanceled;
    @JsonCreator
    public EventJSON(@JsonProperty("EventID") int eventID,
                        @JsonProperty("EventTitle") String eventTitle, 
                            @JsonProperty("EventDescription") String eventDescription,
                                @JsonProperty("EventPin") String eventPin, 
                                    @JsonProperty("EventDate") LocalDateTime eventDate,
                                        @JsonProperty("DatePosted") LocalDateTime datePosted,
                                            @JsonProperty("Clips") Set<VideoJSON> clips,
                                                @JsonProperty("Posters") Set<ImageJSON> posters,
                                                    @JsonProperty("Posts") Set<EventPostJSON> posts,
                                                        @JsonProperty("CanceledImages") Set<ImageJSON> canceledImages,
                                                            @JsonProperty("CanceledClips") Set<VideoJSON> canceledClips,                                                   
                                                            @JsonProperty("EventVenue") String eventVenue,
                                                                @JsonProperty("PostedBy") UserJSON user,
                                                                @JsonProperty("Categories") Set<CategoryJSON> categories,
                                                                @JsonProperty("CanceledCategories") Set<CategoryJSON> canceledCategories){
        this.eventID = eventID;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventPin = eventPin;
        this.eventDate = eventDate;
        this.datePosted = datePosted;
        this.clips = clips;
        this.posters = posters;
        this.posts = posts;
        this.user = user;
        this.eventVenue = eventVenue;
        this.canceledImages = canceledImages;
        this.canceledClips = canceledClips;
        this.isCanceled = false;
        this.categories = categories;
        this.canceledCats = canceledCategories;
    }

    public int getEventID() {
        return this.eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }


    public String getEventTitle() {
        return this.eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return this.eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public void setPostedBy(UserJSON user){
        this.user = user;
    }

    public UserJSON getPostedBy(){
        return this.user;
    }

    @JsonProperty("Posts")
    public Set<EventPostJSON> getPosts(){
        return this.posts;
    }

    @JsonProperty("Posts")
    public void setPosts(Set<EventPostJSON> posts){
        this.posts = posts;
    }

    public Set<VideoJSON> getClips(){
        return this.clips;
    }

    public void setClips(Set<VideoJSON> clips){
        this.clips = clips;
    }

    public Set<ImageJSON> getPosters(){
        return this.posters;
    }

    public void setPosters(Set<ImageJSON> posters){
        this.posters = posters;
    }

    public String getEventPin() {
        return this.eventPin;
    }

    public void setEventPin(String eventPin) {
        this.eventPin = eventPin;
    }

    public LocalDateTime getEventDate() {
        return this.eventDate;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }
    
    public LocalDateTime getDatePosted() {
        return this.datePosted;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventVenue() {
        return this.eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public Set<ImageJSON> getCanceledImages() {
        return canceledImages;
    }

    public void setCanceledImages(Set<ImageJSON> canceledImages) {
        this.canceledImages = canceledImages;
    }

    public Set<VideoJSON> getCanceledClips() {
        return canceledClips;
    }
    
    public void setCanceledClips(Set<VideoJSON> canceledClips) {
        this.canceledClips = canceledClips;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        this.isCanceled = canceled;
    }

    public Set<CategoryJSON> getCanceledCategories(){
        return this.canceledCats;
    }

    public void setCanceledCategories(Set<CategoryJSON> canceledCategories){
        this.canceledCats = canceledCategories;
    }

    public void setCategories(Set<CategoryJSON> categories){
        this.categories = categories;
    }

    public Set<CategoryJSON> getCategories(){
        return this.categories;
    }
    
}
