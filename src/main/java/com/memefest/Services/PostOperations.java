package com.memefest.Services;

import java.util.Set;

import com.memefest.DataAccess.EventPost;
import com.memefest.DataAccess.Repost;
import com.memefest.DataAccess.TopicPost;
import com.memefest.DataAccess.Post;
import com.memefest.DataAccess.PostReply;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import com.memefest.DataAccess.JSON.RepostJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;

import jakarta.persistence.NoResultException;

public interface PostOperations {
    
    public void createPost(PostJSON newPost);

    public void createPostReplies(PostWithReplyJSON post);

    public void removePostReplies(PostWithReplyJSON post);

    public void editPostReplies(PostWithReplyJSON post);

    public void editPostWithReply(PostWithReplyJSON post);

    public void removePostWithReply(PostWithReplyJSON post);

    public void createTopicPost(TopicPostJSON topicPost);

    public TopicPost getTopicPostEntity(TopicPostJSON topicPost) throws NoResultException;

    public void removeTopicPost(TopicPostJSON topicPost);

    public void editTopicPost(TopicPostJSON topicPost);

    public Post getPostEntity(PostJSON post);

    public void editPost(PostJSON post);

    public void removePost(PostJSON post);

    public PostWithReplyJSON getPostWithReplyInfo(PostWithReplyJSON postWithReply);
    
    public PostJSON getPostInfo(PostJSON post);

    public void createRepost(RepostJSON repost);

    public void removeRepost(RepostJSON repost);

    public void createEventPost(EventPostJSON eventPost);

    public void editEventPost(EventPostJSON eventPost);

    public void removeEventPost(EventPostJSON eventPost);

    public EventPost getEventPostEntity(EventPostJSON eventPost);

    public void editRepost(RepostJSON eventRepost);

    public Repost getRepostEntity(RepostJSON repostJSON) throws NoResultException;

    public Set<PostReply> getPostReplyEntities(PostWithReplyJSON post);
    
    public RepostJSON getRepostInfo(RepostJSON repost);

    //public Set<RepostJSON> getReposts(RepostJSON repost);
}
