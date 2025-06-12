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

    public TopicPostNotification getTopicPostNotificationEntity(TopicPostNotificationJSON topicPostNotification) throws NoResultException;

    public PostNotification getPostNotificationEntity(PostNotificationJSON postNotification) throws NoResultException;

    public EventNotification getEventNotificationEntity(EventNotificationJSON eventNotification) throws NoResultException;

    public EventPostNotification getEventPostNotificationEntity(EventPostNotificationJSON eventNotification) throws NoResultException;

    public Set<TopicFollowNotificationJSON> getTopicFollowNotificationInfo(TopicFollowNotificationJSON topicFollowNotification) throws NoResultException;

    public Set<EventPostNotificationJSON> getEventPostNotificationInfo(EventPostNotificationJSON eventPostNotification) throws NoResultException;

    public Set<TopicPostNotificationJSON> getTopicPostNotificationInfo(TopicPostNotificationJSON topicPostNotification) throws NoResultException;
    
    public Set<EventNotificationJSON> getEventNotificationInfo(EventNotificationJSON eventNotification);

    public void editTopicPostNotification(TopicPostNotificationJSON topicPostNot);

    public void editEventNotification(EventNotificationJSON eventNot);

    public void editEventPostNotification(EventPostNotificationJSON eventPostNot);

    public Set<PostNotificationJSON> getPostNotificationInfo(PostNotificationJSON postNot);

    public Set<UserFollowNotificationJSON> getUserFollowNotificationInfo(UserFollowNotificationJSON followNot);

    public void editTopicFollowNotification(TopicFollowNotificationJSON topicFollowNot);

    public void editUserFollowNotification(UserFollowNotificationJSON userFollowNot);

    public void editPostNotification(PostNotificationJSON postNot);
}
