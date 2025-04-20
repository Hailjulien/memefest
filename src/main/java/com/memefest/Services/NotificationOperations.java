package com.memefest.Services;

import java.util.Set;

import com.memefest.DataAccess.EventNotification;
import com.memefest.DataAccess.EventPostNotification;
import com.memefest.DataAccess.PostNotification;
import com.memefest.DataAccess.TopicPostNotification;
import com.memefest.Websockets.JSON.EventNotificationJSON;
import com.memefest.Websockets.JSON.EventPostNotificationJSON;
import com.memefest.Websockets.JSON.PostNotificationJSON;
import com.memefest.Websockets.JSON.TopicFollowNotificationJSON;
import com.memefest.Websockets.JSON.TopicPostNotificationJSON;
import com.memefest.Websockets.JSON.UserFollowNotificationJSON;

import jakarta.persistence.NoResultException;

public interface NotificationOperations {
   
    public void createTopicPostNotification(TopicPostNotificationJSON topicPostNotification);

    public TopicPostNotification getTopicPostNotificationEntity(TopicPostNotificationJSON topicPostNotification) throws NoResultException;

    public PostNotification getPostNotificationEntity(PostNotificationJSON postNotification) throws NoResultException;
    
    public void createEventNotification(EventNotificationJSON postNotification) ;

    public EventNotification getEventNotificationEntity(EventNotificationJSON eventNotification) throws NoResultException;

    public void createEventPostNotification(EventPostNotificationJSON eventPostNotification);

    public EventPostNotification getEventPostNotificationEntity(EventPostNotificationJSON eventNotification) throws NoResultException;

    public TopicFollowNotificationJSON getTopicFollowNotificationInfo(TopicFollowNotificationJSON topicFollowNotification) throws NoResultException;

    public UserFollowNotificationJSON getUserFollowNotificationInfo(UserFollowNotificationJSON userFollowNotification);

    public void removePostNotification(PostNotificationJSON postNotification);

    public void removeEventPostNotification(EventPostNotificationJSON eventPostNotification);

    public void removeTopicPostNotification(TopicPostNotificationJSON topicPostNotification);

    public void removeEventNotification(EventNotificationJSON eventNotification);

    public Set<EventPostNotificationJSON> getEventPostNotificationInfo(EventPostNotificationJSON eventPostNotification) throws NoResultException;

    public Set<TopicPostNotificationJSON> getTopicPostNotificationInfo(TopicPostNotificationJSON topicPostNotification) throws NoResultException;
    
}
