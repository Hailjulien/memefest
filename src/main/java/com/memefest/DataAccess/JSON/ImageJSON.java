package com.memefest.DataAccess.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonRootName("Image")
@JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "ImageId")
public class ImageJSON {
    
    @JsonProperty("ImageId")
    private int imgId;

    @JsonProperty("ImagePath")
    private String imgPath;

    @JsonProperty("ImageTitle")
    private String imgTitle;

    @JsonProperty("IsCanceled")
    private boolean isCanceled;

    @JsonCreator
    public ImageJSON(@JsonProperty("ImageId") int imgId, 
                        @JsonProperty("ImagePath") String imgPath,
                            @JsonProperty("ImageTitle") String imgTitle) {
        this.imgId = imgId;
        this.imgPath = imgPath;
        this.imgTitle = imgTitle;
        this.isCanceled = false;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        this.isCanceled = canceled;
    }
    
}
