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
import com.memefest.DataAccess.EventPostId;
import com.memefest.DataAccess.Post;
import com.memefest.DataAccess.PostReply;
import com.memefest.DataAccess.Repost;
import com.memefest.DataAccess.Topic;
import com.memefest.DataAccess.TopicPost;
import com.memefest.DataAccess.User;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import com.memefest.DataAccess.JSON.RepostJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Services.EventOperations;
import com.memefest.Services.PostOperations;
import com.memefest.Services.TopicOperations;
import com.memefest.Services.UserOperations;
import com.memefest.Websockets.JSON.PostNotificationJSON;

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
    private FeedsEndPointService feedEndPointService;

    @EJB
    private EventOperations eventOperations;

    @EJB
    private TopicOperations topicOperations;

    private void createPostEntity(Post post) {
        this.entityManager.persist(post);
    }
    

    public void createPost(PostJSON post) {
        User user = null;
        user = this.userOperations.getUserEntity(post.getUser());
        if (user == null)
          return;
        Post newPost = new Post();
        newPost.setComment(post.getComment());
        newPost.setCreated(Date.from(post.getCreated().atZone(ZoneId.systemDefault()).toInstant()));
        newPost.setUser(user);
        newPost.setUpvotes(post.getUpvotes());
        newPost.setDownvotes(post.getDownvotes());
        createPostEntity(newPost);
        if (post instanceof PostWithReplyJSON)
            createPostReplies((PostWithReplyJSON)post);
        PostNotificationJSON postNot = new PostNotificationJSON(3, post, LocalDateTime.now());
        feedEndPointService.sendToAll(postNot); 
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
        if (event == null) { 
        return;
        }
        createPost(post);
    
        EventPost  eventPostEntity = new EventPost();
        eventPostEntity.setEvent(event);
        eventPostEntity.setPost(getPostEntity(post));
        entityManager.persist(eventPostEntity);
    }

    public void editEventPost(EventPostJSON eventPost){
        EventPost eventPostEntity = null;
        try{
            eventPostEntity = getEventPostEntity(eventPost);
        } catch(NoResultException e){
            createEventPost(eventPost);
        }
        eventPostEntity.setEvent(eventOperations.getEventEntity(eventPost.getEvent()));
        eventPostEntity.setPost(getPostEntity((PostJSON)eventPost));
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

        PostNotificationJSON postNotificaation = new PostNotificationJSON(1, post, LocalDateTime.now());
        UserJSON user  = post.getUser();
        user = userOperations.getUserInfo(user);
        feedEndPointService.sendToUser(postNotificaation, post.getUser().getUsername());
        if (postInst instanceof PostWithReplyJSON)
            createPostReplies((PostWithReplyJSON)postInst); 
        } 
    }



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
    public void editPostWithReply(PostWithReplyJSON postWithReply){
        Post post = getPostEntity((PostJSON)postWithReply);
        if (post == null){
      createPost(postWithReply);
      editPostWithReply(postWithReply);
        return;
        }
        createPostReplies(postWithReply);
        editPostReplies(postWithReply);
        removePostReplies(postWithReply);
        removePostWithReply(postWithReply); 
    }

    public void removePostWithReply(PostWithReplyJSON postWithReply){
        if(postWithReply.isCancelled()){
        Post postEntity = getPostEntity((PostJSON)postWithReply);
        if (postEntity != null && postEntity.getPost_Id()!= 0) {
            this.entityManager.remove(postEntity);
        }
        }
    }

    public void createTopicPost(TopicPostJSON topicPost){
        Topic topic = topicOperations.getTopicEntity(topicPost.getTopic());
        if (topic == null)
            topicOperations.createTopic(topicPost.getTopic()); 
        createPost((PostJSON) topicPost);
        Post post = getPostEntity((PostJSON)topicPost);
        if (post == null)
            return; 
        TopicPost topicPostEntity = new TopicPost();
        topicPostEntity.setPost(post);
        topicPostEntity.setTopic(topic);
        this.entityManager.persist(topicPostEntity);
    }

    public TopicPost getTopicPostEntity(TopicPostJSON post) throws NoResultException{
        if(post.getPostId() != 0)
            return (TopicPost)this.entityManager.find(TopicPost.class, Integer.valueOf(post.getPostId()));
        if (post.getComment() != null){
            Query query = this.entityManager.createNamedQuery("Post.getPostByComment");
            query.setParameter("comment", post.getComment());
            return (TopicPost)query.getSingleResult();
        }
        throw new NoResultException("No TopicPost found for post" + post.getPostId());
    }
 
    public void removeTopicPost(TopicPostJSON topicPost){
        if(topicPost.isCancelled() == false)
            return;
        TopicPost topicPostEntity = getTopicPostEntity(topicPost);
        if (topicPostEntity!= null)
        this.entityManager.remove(topicPostEntity);
    }

    public void editTopicPost(TopicPostJSON topicPost){
        TopicPost topicPostEntity = null;
        try{
            topicPostEntity = getTopicPostEntity(topicPost);
        } catch(NoResultException e){
            createTopicPost(topicPost);
        }
        topicPostEntity.setTopic(topicOperations.getTopicEntity(topicPost.getTopic()));
        topicPostEntity.setPost(getPostEntity((PostJSON)topicPost));
        this.entityManager.merge(topicPostEntity);
        removeTopicPost(topicPost);
    }
  
    public Post getPostEntity(PostJSON post) {
        if (post.getPostId() != 0 && post.getPostId() != 1)
            return (Post)this.entityManager.find(Post.class, Integer.valueOf(post.getPostId())); 
        if (post.getComment() != null) {
            Query query = this.entityManager.createNamedQuery("Post.getPostByComment");
            query.setParameter("comment", post.getComment());
            return (Post)query.getSingleResult();
        } 
        return null;
    }
  
    public void removePost(PostJSON post) {
        if (post.isCancelled()) {
            Post postEntity = getPostEntity(post);
            if (postEntity != null) {
                if (post instanceof PostWithReplyJSON)
                    removePostReplies((PostWithReplyJSON)post); 
                this.entityManager.remove(postEntity);
            } 
        } 
    }

    public Set<PostReply> getPostReplyEntities(PostWithReplyJSON postWithReply) {
        Set<PostReply> result = new HashSet<>();
        Query query = this.entityManager.createNamedQuery("PostReplyEntity.getRepliesOfPostId");
        query.setParameter("postId", Integer.valueOf(postWithReply.getPostId()));
        List<PostReply> postReplies = query.getResultList();
        result = (Set<PostReply>)postReplies.stream().collect(Collectors.toSet());
        return result;
    }
  
    public PostWithReplyJSON getPostWithReplyInfo(PostWithReplyJSON postWithReply) {
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
        EventPostId eventPostId = new EventPostId();
        eventPostId.setEvent_Id(event.getEvent().getEventID());
        eventPostId.setPost_Id(event.getPostId());
        EventPost eventPost = this.entityManager.find(EventPost.class,eventPostId); 
        return eventPost;
    }

    public PostJSON getPostInfo(PostJSON post) {
        Post postEntity = getPostEntity(post);
        UserJSON user = this.userOperations.getUserInfo(post.getUser());
        if (post != null) {
            PostJSON postJSON = new PostJSON(postEntity.getPost_Id(), postEntity.getComment(), LocalDateTime.ofInstant(postEntity.getCreated().toInstant(), ZoneId.systemDefault()), post.getUpvotes(), post.getDownvotes(), new UserJSON(user.getUsername()));
            return postJSON;
        } 
    return null;
  }

}
