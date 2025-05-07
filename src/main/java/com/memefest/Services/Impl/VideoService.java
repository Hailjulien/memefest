package com.memefest.Services.Impl;

import com.memefest.DataAccess.Video;
import com.memefest.DataAccess.JSON.VideoJSON;
import com.memefest.Services.VideoOperations;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.Query;

@Stateless(name = "VideoService")
public class VideoService implements VideoOperations{

    @PersistenceContext(unitName = "memeFest", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager; 

    public void createVideo(VideoJSON video){
        Video videoEntity = new Video();
        videoEntity.setVid_Path(video.getVidPath());
        videoEntity.setVid_Title(video.getTitle());
        entityManager.persist(videoEntity);    
    }

    public void editVideo(VideoJSON video){
        try{
            Video videoEntity = getVideoEntity(video);
            if(video.getVidPath() != null && !video.getVidPath().equalsIgnoreCase(videoEntity.getVid_Path()))
                videoEntity.setVid_Path(video.getVidPath());
            if(video.getVidId() != 0 || video.getVidId() != 1){
                videoEntity.setVid_Id(video.getVidId());
            }
            if(video.getTitle()!= null && !video.getTitle().equalsIgnoreCase(videoEntity.getVid_Title()))
                videoEntity.setVid_Title(video.getTitle());
            entityManager.merge(videoEntity);
            removeVideo(video);
        }
        catch(NoResultException ex){
            createVideo(video);
        }
    }

    public void removeVideo(VideoJSON video){
        if(!video.isCanceled())
            return;
        try{
            Video videoEntity = getVideoEntity(video);
            entityManager.remove(videoEntity);
        }
        catch(NoResultException ex){
            return;
        }
    }

    public Video getVideoEntity(VideoJSON video) throws NoResultException{
        if (video.getTitle() == null && video.getVidId() == 0)
            throw new NoResultException("Video not found"); 
        Video videoEntity = null;
        try{
            if(video.getVidId() != 0 )
             videoEntity = entityManager.find(Video.class, video.getVidId());
        }
        catch(NoResultException ex){
            Query query = entityManager.createNamedQuery("Video.getVideoByTitle", Video.class);
            query.setParameter("title", video.getTitle());
            videoEntity = (Video) query.getSingleResult();
            return videoEntity;
        }
        return videoEntity;
    }

    public VideoJSON getVideoInfo(VideoJSON video){
        try{
            Video videoEntity = getVideoEntity(video);
            VideoJSON videoJSON = new VideoJSON(videoEntity.getVid_Id(), videoEntity.getVid_Path(), videoEntity.getVid_Title());
            return videoJSON;
        }
        catch(NoResultException ex){
            return null;
        }
    }
}
