package com.memefest.Services;

import java.util.Set;

import com.memefest.DataAccess.Event;
import com.memefest.DataAccess.EventPost;
import com.memefest.DataAccess.Repost;
import com.memefest.DataAccess.TopicPost;
import com.memefest.DataAccess.User;
import com.memefest.DataAccess.Post;
import com.memefest.DataAccess.PostReply;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import com.memefest.DataAccess.JSON.RepostJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;

import jakarta.persistence.NoResultException;

public interface PostOperations {


    public void editPostReplies(PostWithReplyJSON post);

    public void editPostWithReply(PostWithReplyJSON post);

    public TopicPost getTopicPostEntity(TopicPostJSON topicPost) throws NoResultException;

    public void editTopicPost(TopicPostJSON topicPost);

    public Post getPostEntity(PostJSON post);

    public void editPost(PostJSON post);

    public PostWithReplyJSON getPostWithReplyInfo(PostWithReplyJSON postWithReply);
    
    public PostJSON getPostInfo(PostJSON post);

    public void removeRepost(RepostJSON repost);

    public void editEventPost(EventPostJSON eventPost);

    public EventPost getEventPostEntity(EventPostJSON eventPost);

    public void editRepost(RepostJSON eventRepost);

    public Repost getRepostEntity(Post post, User owner) throws NoResultException;

    public Set<PostReply> getPostReplyEntities(PostWithReplyJSON post);
    
    public RepostJSON getRepostInfo(RepostJSON repost);

    public TopicPostJSON getTopicPostInfo(TopicPostJSON topicPost);

    public EventPostJSON getEventPostInfo(EventPostJSON eventPost);

    public Set<PostJSON> searchPost(PostJSON post);

    //public Set<RepostJSON> getReposts(RepostJSON repost);
}
