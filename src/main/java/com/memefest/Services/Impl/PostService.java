package com.memefest.Services.Impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.memefest.DataAccess.Event;
import com.memefest.DataAccess.EventPost;
import com.memefest.DataAccess.Post;
import com.memefest.DataAccess.PostReply;
import com.memefest.DataAccess.Repost;
import com.memefest.DataAccess.Topic;
import com.memefest.DataAccess.TopicPost;
import com.memefest.DataAccess.User;
import com.memefest.DataAccess.JSON.EventJSON;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import com.memefest.DataAccess.JSON.RepostJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Services.FeedsOperations;
import com.memefest.Services.NotificationOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Services.UserOperations;
import com.memefest.Websockets.JSON.EventPostNotificationJSON;
import com.memefest.Websockets.JSON.PostNotificationJSON;
import com.memefest.Websockets.JSON.TopicPostNotificationJSON;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.Query;

@Stateless(name = "PostService")
public class PostService implements PostOperations{
    
    @PersistenceContext(unitName = "memeFest", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @EJB
    private UserOperations userOperations;

    @EJB
    private FeedsOperations feedEndPointService;

    @EJB
    private EventOperations eventOperations;

    @EJB
    private TopicOperations topicOperations;

    @EJB
    private NotificationOperations notOps;

    private void createPostEntity(Post post) {
        this.entityManager.persist(post);
    }
    
    //add custom exception to show object was not created
    public void createPost(PostJSON post) {
        User user = null;
        try{
            user = this.userOperations.getUserEntity(post.getUser());
            Post newPost = new Post();
            newPost.setComment(post.getComment());
            newPost.setCreated(Date.from(post.getCreated().atZone(ZoneId.systemDefault()).toInstant()));
            newPost.setUser(user);
            newPost.setUpvotes(post.getUpvotes());
            newPost.setDownvotes(post.getDownvotes());
            createPostEntity(newPost);
            userOperations.getFollowing(post.getUser()).stream().map(candidate ->{
                return new PostNotificationJSON(0, post, LocalDateTime.now(), candidate);
            }).forEach(candidate ->{
                feedEndPointService.sendToUser(newPost, candidate.getUser().getUsername());
            });;
        }
        catch(NoResultException ex){
            return;
        }

    }

    public void createEventPost(EventPostJSON post){
        Event event = null;
        try {
            event = eventOperations.getEventEntity(post.getEvent());  
        } catch (NoResultException e) {
        //find if user is an admin then execute createtopic otherwise exit method 
        eventOperations.createEvent(post.getEvent());
        createEventPost(post);      
        }
        EventPost  eventPostEntity = new EventPost();
        eventPostEntity.setEvent(event);
        entityManager.persist(eventPostEntity);
        EventPostNotificationJSON eventPostNot = new EventPostNotificationJSON(0, post, LocalDateTime.now(),  null);
        feedEndPointService.sendToAll(eventPostNot);
    }

    //add custom exception to show object was not created
    public void editEventPost(EventPostJSON eventPost){
        EventPost eventPostEntity = null;
        try{
            eventPostEntity = getEventPostEntity(eventPost);
        } catch(NoResultException e){
            createEventPost(eventPost);
        }
        eventPostEntity.setEvent(eventOperations.getEventEntity(eventPost.getEvent()));
        this.entityManager.merge(eventPostEntity);
        removeEventPost(eventPost);
    }

    public void removeEventPost(EventPostJSON eventPost){
        if(eventPost.isCancelled() == false)
            return;
        EventPost eventPostEntity = null;
        try{
            eventPostEntity = getEventPostEntity(eventPost);
        } catch(NoResultException e){
            return;
        }
        this.entityManager.remove(eventPostEntity);
    }

    //add custom exception to show object was not created
    public void createRepost(RepostJSON repost){
        Post post = null;
        User user = null;
        try{
            post = getPostEntity((PostJSON)repost.getPost());
        }
        catch(NoResultException ex){
            return;
        }
        try{
            user = userOperations.getUserEntity(repost.getUser());
        }
        catch(NoResultException ex){
            return;
        } 
        Repost repostEntity = new Repost();
        repostEntity.setUser(user);
        repostEntity.setPost(post);
        this.entityManager.persist(repostEntity);
    }
  
    public Repost getRepostEntity(RepostJSON repost) throws NoResultException{
        if(repost.getRepostId() != 0)
            return entityManager.find(Repost.class, repost.getRepostId());
        throw new NoResultException("No Repost found with rePostId" + repost.getRepostId()); 
    }

    public RepostJSON getRepostInfo(RepostJSON repost){
        Repost repostEntity = null;
        try{
            repostEntity = getRepostEntity(repost);
        }
        catch(NoResultException ex){
            return null;
        }
        Post postEntity = repostEntity.getPost();
        User userEntity = repostEntity.getUser();
        UserJSON user = userOperations.getUserInfo(new UserJSON( userEntity.getUserId(), userEntity.getUsername()));
        PostJSON post = getPostInfo(new PostJSON(postEntity.getPost_Id(), null, null, 0, 0,null));
        RepostJSON repostInfo = new RepostJSON(repostEntity.getRepostId(), post, user);
        return repostInfo;
    }

    public EventPostJSON getEventPostInfo(EventPostJSON eventPost) throws NoResultException{
        EventPost eventPostEntity = getEventPostEntity(eventPost);
        eventPost.setComment(eventPostEntity.getComment());
        eventPost.setCreated(LocalDateTime.ofInstant(eventPostEntity.getCreated().toInstant(), ZoneId.systemDefault()));
        eventPost.setDownvotes(eventPostEntity.getDownvotes());
        eventPost.setUpvotes(eventPostEntity.getUpvotes());
        eventPost.setPostId(eventPostEntity.getPost_Id());
        eventPost.setUser(new UserJSON(eventPostEntity.getUser().getUserId(), eventPostEntity.getUser().getUsername()));
        eventPost.setEvent(new EventJSON(eventPostEntity.getEvent().getEvent_Id(), eventPostEntity.getEvent().getEvent_Title(), null, null, null, null, null, null, null, null, null, null));
        return eventPost;
    }

    //add custom exception to show object was not created
    public void editRepost(RepostJSON post){
        Repost repostEntity = null;
        Post postEntity = null;
        User user = null;
        try{
            repostEntity = getRepostEntity(post);
        }
        catch(NoResultException ex){
            createRepost(post);
            editRepost(post);
        }
        try{
            postEntity = getPostEntity((PostJSON)post.getPost());
        }
        catch(NoResultException ex){
            createPost(post.getPost());
            editRepost(post);
        }
        try{
            user = userOperations.getUserEntity(post.getUser());
        }
        catch(NoResultException ex){
            return;
        }
        repostEntity.setPost(postEntity);
        repostEntity.setUser(user);
        this.entityManager.merge(repostEntity);
        removeRepost(post);
    }

    public void removeRepost(RepostJSON post){
        if(post.isCanceled() ==  false)
            return;
        try{
            Repost repostEntity = getRepostEntity(post);
            this.entityManager.remove(repostEntity);
        }
        catch(NoResultException ex){
            return;
        }
    }


    //add custom exception to show object was not created
    public void createPostReplies(PostWithReplyJSON post) {
        for (PostJSON postInst : post.getPosts()) {
            Post parent = getPostEntity((PostJSON)post);
            if (parent == null)
                return; 
            Post child = getPostEntity(postInst);
            if (child != null)
                continue; 
            PostReply postReply = new PostReply();
            postReply.setComment(postInst.getComment());
            postReply.setCreated(Date.from(postInst.getCreated().atZone(ZoneId.systemDefault()).toInstant()));
            postReply.setPost(parent);
            postReply.setUser(this.userOperations.getUserEntity(postInst.getUser()));
            postReply.setDownvotes(postInst.getDownvotes());
            postReply.setUpvotes(postInst.getUpvotes());
            this.entityManager.persist(postReply);
        }
    }

    //add custom exception to show object was not created
    public void editPostReplies(PostWithReplyJSON post){
        Set<PostReply> postEntities = null;
        try{
            postEntities = getPostReplyEntities(post);
        for(PostReply postReply : postEntities){
            for(PostJSON postReplyJSON : post.getPosts()){
                if(postReplyJSON.getPostId() == postReply.getPost_Info()){
                    postReply.setComment(postReplyJSON.getComment());
                    postReply.setCreated(Date.from(postReplyJSON.getCreated().atZone(ZoneId.systemDefault()).toInstant()));
                    postReply.setDownvotes(postReplyJSON.getDownvotes());
                    postReply.setUpvotes(postReplyJSON.getUpvotes());
                    postReply.setUser(this.userOperations.getUserEntity(postReplyJSON.getUser()));
                    this.entityManager.merge(postReply);
                    if(postReplyJSON instanceof PostWithReplyJSON){
                        editPostReplies((PostWithReplyJSON)postReplyJSON);
                    }
                }
            }
        }
        }catch(NoResultException ex){
            createPostReplies(post);
            editPostReplies(post);
            return;
        }
        removePostReplies(post);
    }
  
    public void removePostReplies(PostWithReplyJSON postWithReply) {
        for (PostJSON post : postWithReply.getPosts()) {
            if (post.isCancelled()) {
                Post postEntity = getPostEntity(post);
                if (postEntity != null)
                    this.entityManager.remove(postEntity); 
            } 
        } 
    }
    
    //add custom exception to show object was not created
    public void editPost(PostJSON post) {
        Post postEntity = null;
        try{
            postEntity = getPostEntity(post);
        }catch(NoResultException ex){
            createPost(post);
            editPost(post);
            return;
        }
        User user = this.userOperations.getUserEntity(post.getUser());
        //check if user is admin then execute create topic
        if (user != null)
            postEntity.setUser(user); 
        if (post.getComment() != null)
            postEntity.setComment(post.getComment());  
        if (post.getCreated() != null)
            postEntity.setCreated(Date.from(post.getCreated().atZone(ZoneId.systemDefault()).toInstant())); 
        if (postEntity.getDownvotes() != 0)
            postEntity.setDownvotes(post.getDownvotes()); 
        if (postEntity.getUpvotes() != 0)
            postEntity.setUpvotes(post.getUpvotes()); 
        this.entityManager.merge(postEntity);
        removePost(post);
    }

    //add custom exception to show object was not created
    public void editPostWithReply(PostWithReplyJSON postWithReply){       
        try{
            getPostEntity((PostJSON)postWithReply);
            editPost(postWithReply);
        }
        catch(NoResultException ex){
            createPost(postWithReply);
            editPostWithReply(postWithReply);
        }
        createPostReplies(postWithReply);
        editPostReplies(postWithReply);
        removePostReplies(postWithReply);
        removePostWithReply(postWithReply); 
    }

    public void removePostWithReply(PostWithReplyJSON postWithReply){
        if(postWithReply.isCancelled()){
            try{
                Post postEntity = getPostEntity((PostJSON)postWithReply);
                if (postEntity != null && postEntity.getPost_Id()!= 0) {
                    this.entityManager.remove(postEntity);
                }
            }
            catch(NoResultException ex){
                return;
            }
        }
    }

    //add custom exception to show object was not created
    public void createTopicPost(TopicPostJSON topicPost){
        Topic topic = null;
        try{
            topic = topicOperations.getTopicEntity(topicPost.getTopic());
        }
        catch(NoResultException ex){
            topicOperations.createTopic(topicPost.getTopic());
            try{
                topic = topicOperations.getTopicEntity(topicPost.getTopic());
            }
            catch(NoResultException exp){
                return;
            }
            createTopicPost(topicPost); 
        }
        createPost((PostJSON) topicPost);
        Post post = null;
        try{
            post = getPostEntity(topicPost);
        }
        catch(NoResultException ex){
            createPost(topicPost);
            try{
                post = getPostEntity(topicPost);
            }
            catch(NoResultException exp){
                return;
            }
            createTopicPost(topicPost); 
        } 
        TopicPost topicPostEntity = new TopicPost();
        topicPostEntity.setTopic(topic);
        this.entityManager.persist(topicPostEntity);
        topic.getFollowedBy().stream().forEach(candidate->{
            notOps.createTopicPostNotification(new TopicPostNotificationJSON(0, topicPost,LocalDateTime.now(), new UserJSON(candidate.getUser().getUsername())));
        });
        
    }

    public TopicPost getTopicPostEntity(TopicPostJSON post) throws NoResultException{
        if(post.getPostId() != 0)
            return (TopicPost)this.entityManager.find(TopicPost.class, Integer.valueOf(post.getPostId()));
        if (post.getComment() != null){
            Query query = this.entityManager.createNamedQuery("Post.getPostByComment");
            query.setParameter("comment", "%" + post.getComment()  + "%");
            return (TopicPost)query.getSingleResult();
        }
        throw new NoResultException("No TopicPost found for post" + post.getPostId());
    }

    public TopicPostJSON getTopicPostInfo(TopicPostJSON topicPost) throws NoResultException{
        TopicPost topicPostEntity = getTopicPostEntity(topicPost);
        return new TopicPostJSON(topicPostEntity.getPost_Id(), 
                            topicPostEntity.getComment(), 
                            LocalDateTime.ofInstant(topicPostEntity.getCreated().toInstant(), ZoneId.systemDefault()), 
                            topicPostEntity.getUpvotes(), 
                            topicPostEntity.getDownvotes(),
                            new UserJSON(topicPostEntity.getUserId(), null), new TopicJSON(topicPostEntity.getTopic().getTopic_Id(), null, null, null, null, null));
    }

    public PostJSON getPostInfo(PostJSON post) throws NoResultException{
        Post postInfo = getPostEntity(post);
        return new PostJSON(post.getPostId(), post.getComment(), 
        LocalDateTime.ofInstant(postInfo.getCreated().toInstant(), ZoneId.systemDefault()), 
            post.getUpvotes(), post.getDownvotes(), new UserJSON(post.getUser().getUserId(), post.getUser().getUsername()));
    }
 
    public void removeTopicPost(TopicPostJSON topicPost){
        if(topicPost.isCancelled() == false)
            return;
        try{
            TopicPost topicPostEntity = getTopicPostEntity(topicPost);
            this.entityManager.remove(topicPostEntity);
        }
        catch(NoResultException ex){
            return;
        }
    }

    //add custom exception to show object was not created
    public void editTopicPost(TopicPostJSON topicPost){
        TopicPost topicPostEntity = null;
        try{
            topicPostEntity = getTopicPostEntity(topicPost);
        } catch(NoResultException e){
            createTopicPost(topicPost);
        }
        topicPostEntity.setTopic(topicOperations.getTopicEntity(topicPost.getTopic()));
        this.entityManager.merge(topicPostEntity);
        removeTopicPost(topicPost);
    }
  
    public Post getPostEntity(PostJSON post) throws NoResultException {
        Post postEntity = null;
        if (post.getPostId() != 0 && post.getPostId() != 1)
            postEntity = this.entityManager.find(Post.class, Integer.valueOf(post.getPostId()));
        if (postEntity != null)
            return postEntity;
        if (post.getComment() != null) {
            Query query = this.entityManager.createNamedQuery("Post.getPostByComment");
            query.setParameter("comment", "%" + post.getComment() + "%");
            postEntity =(Post) query.getSingleResult();
        }
        return postEntity; 
    }
  
    public void removePost(PostJSON post) {
        if (post.isCancelled()) {
            try{
                Post postEntity = getPostEntity(post);
                if (postEntity != null) {
                    if (post instanceof PostWithReplyJSON)
                        removePostReplies((PostWithReplyJSON)post); 
                    this.entityManager.remove(postEntity);
                }
            }
            catch(NoResultException ex){
                return;
            } 
        } 
    }

    public Set<PostReply> getPostReplyEntities(PostWithReplyJSON postWithReply) throws NoResultException{
        Set<PostReply> result = new HashSet<>();
        List<PostReply> postReplies = this.entityManager.createNamedQuery("PostReplyEntity.getRepliesOfPostId", PostReply.class)
                                        .setParameter("postId", Integer.valueOf(postWithReply.getPostId()))
                                            .getResultList();
        result = (Set<PostReply>)postReplies.stream().collect(Collectors.toSet());
        return result;
    }
  
    public PostWithReplyJSON getPostWithReplyInfo(PostWithReplyJSON postWithReply) throws NoResultException{
        Set<PostReply> postReplyEntities = getPostReplyEntities(postWithReply);
        if (postReplyEntities == null)
            return null; 
        Set<PostJSON> postReplys = (Set<PostJSON>)postReplyEntities.stream().map(candidate -> 
                                  new PostJSON(candidate.getPost_Id(), null, null, 0, 0, null))
                                    .collect(Collectors.toSet());
        postWithReply.setPosts(postReplys);
        return postWithReply;
    }

    public EventPost getEventPostEntity(EventPostJSON event) throws NoResultException{
        if(event.getEvent().getEventID() == 0 || event.getPostId() == 0){
            throw new NoResultException("No result for EventPost");
        }
        Post post = getPostEntity(event);
        EventPost eventPost = this.entityManager.find(EventPost.class,post.getPost_Id()); 
        return eventPost;
    }

}
