package com.memefest.Services;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.Locale.Category;

import com.memefest.DataAccess.Topic;
import com.memefest.DataAccess.TopicCategory;
import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicJSON;

import jakarta.ejb.Local;

@Local
public interface TopicOperations {

    public void createTopic(TopicJSON topic);

    public Set<CategoryJSON> getTopicCategories(TopicJSON topic);

    public void createTopicCategories(TopicJSON topic);

    public void removeTopicCategories(TopicJSON topic);

    public void createTopicFollowers(TopicJSON topic);

    public void removeTopicFollowers(TopicJSON topic);

    public void editTopicFollowers(TopicJSON topic);

    public void editTopic(TopicJSON topic);

    public void removeTopic(TopicJSON topic);

    public TopicJSON getTopicInfo(TopicJSON topic);

    public void createScheduledTopic(TopicJSON topic, LocalDateTime postDate);

    public void cancelScheduledTopic(TopicJSON topic);

    public Topic getTopicEntity(TopicJSON topic);

    public Set<TopicJSON> searchTopic(TopicJSON topic);

    //public void getScheduledTopics(TopicJSON topic);

    //public Set<TopicJSON> getTrendingTopics();

    //public Set<PostJSON> getTopicPosts(TopicJSON topic);
}
