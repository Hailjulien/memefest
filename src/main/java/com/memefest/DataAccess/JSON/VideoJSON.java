package com.memefest.DataAccess.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "VideoID")
@JsonRootName("Clip")
public class VideoJSON {
    
    @JsonProperty("ClipID")
    private int vidId;

    @JsonProperty("ClipPath")
    private String vidPath;

    @JsonProperty("ClipTitle") 
    private String title;

    @JsonProperty("Canceled")
    private boolean isCanceled;

    @JsonCreator
    public VideoJSON(@JsonProperty("ClipID") int vidId,
                        @JsonProperty("ClipPath") String vidPath, 
                            @JsonProperty("ClipTitle") String title) {
        this.vidId = vidId;
        this.vidPath = vidPath;
        this.title = title;
        this.isCanceled = false;
    }

    public int getVidId() {
        return vidId;
    }

    public void setVidId(int vidId) {
        this.vidId = vidId;
    }

    public String getVidPath() {
        return vidPath;
    }
    
    public void setVidPath(String vidPath) {
        this.vidPath = vidPath;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        this.isCanceled = canceled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
