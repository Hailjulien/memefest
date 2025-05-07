package com.memefest.Services;

import com.memefest.DataAccess.Image;
import com.memefest.DataAccess.JSON.ImageJSON;

public interface ImageOperations {
    
    public void createImage(ImageJSON image);

    public void editImage(ImageJSON o);

    public void removeImage(ImageJSON video);

    public Image getImageEntity(ImageJSON video);

    public ImageJSON getImageInfo(ImageJSON video);
}
