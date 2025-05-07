package com.memefest.Services.Impl;

import com.memefest.DataAccess.Image;
import com.memefest.DataAccess.JSON.ImageJSON;
import com.memefest.Services.ImageOperations;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.Query;

@Stateless(name =  "ImageServide")
public class ImageService implements ImageOperations{
    
    @PersistenceContext(unitName = "memeFest", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager; 

    public void createImage(ImageJSON image){  
        Image imageEntity = new Image();
        imageEntity.setImg_Path(image.getImgPath());
        imageEntity.setImg_Title(image.getImgTitle());
        entityManager.persist(imageEntity);
    }

    public void editImage(ImageJSON image){
        try{
            Image imageEntity = getImageEntity(image);
            if(image.getImgPath() != null && !image.getImgPath().equalsIgnoreCase(imageEntity.getImg_Path()))
                imageEntity.setImg_Path(image.getImgPath());
            if(image.getImgId() != 0 || image.getImgId() != 1){
                imageEntity.setImg_Id(image.getImgId());
            }
            if(imageEntity.getImg_Title() != null && !image.getImgTitle().equalsIgnoreCase(imageEntity.getImg_Title()))
                imageEntity.setImg_Title(image.getImgTitle());
            entityManager.merge(imageEntity);
            removeImage(image);
        }
        catch(NoResultException ex){
            createImage(image);
        }
    }

    public void removeImage(ImageJSON image){
        if(!image.isCanceled())
            return;
        try{
            Image imageEntity = getImageEntity(image);
            entityManager.remove(imageEntity);
        }
        catch(NoResultException ex){
            return;
        }
    } 

    public Image getImageEntity(ImageJSON image) throws NoResultException{
        if (image.getImgTitle() == null && image.getImgId() == 0)
            throw new NoResultException("Image not found"); 
        Image imageEntity = null;
        try{
            if(image.getImgId() != 0 )
            imageEntity = entityManager.find(Image.class, image.getImgId());
        }
        catch(NoResultException ex){
            Query query = entityManager.createNamedQuery("Image.getImgeByTitle", Image.class);
            query.setParameter("title", "%" + image.getImgTitle() + "%");
            imageEntity = (Image) query.getSingleResult();
            return imageEntity;
        }
        throw new NoResultException("Image not found");
    }

    public ImageJSON getImageInfo(ImageJSON image){
        try{
            Image imageEntity = getImageEntity(image);
            ImageJSON imageJSON = new ImageJSON(imageEntity.getImg_Id(), 
                                    imageEntity.getImg_Path(), imageEntity.getImg_Title());
            return imageJSON;
        }
        catch(NoResultException ex){
            return null;
        }        
    } 

}
