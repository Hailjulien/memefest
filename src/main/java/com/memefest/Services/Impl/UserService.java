package com.memefest.Services.Impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.memefest.DataAccess.CategoryFollower;
import com.memefest.DataAccess.Post;
import com.memefest.DataAccess.TopicFollower;
import com.memefest.DataAccess.User;
import com.memefest.DataAccess.UserAdmin;
import com.memefest.DataAccess.UserSecurity;
import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicJSON;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.DataAccess.JSON.UserSecurityJSON;
import com.memefest.Services.UserOperations;
import com.memefest.Services.UserSecurityService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.persistence.Query;
/*  
@DataSourceDefinition(
  name = "java:app/jndi/memefest/dataSource",
  portNumber = 1433,
  serverName = "localhost",
  user = "Neutron",
  databaseName = "MemeFest",
  password = "scoobyDoo24",
  className = "com.microsoft.sqlserver.jdbc.SQLServerDataSource"
)
*/
//add role based security
@Stateless(name = "userservice")
public class UserService implements UserSecurityService, UserOperations{
    @PersistenceContext(unitName = "memeFest", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    public void createUser(User user) {
        entityManager.persist(user);
    }
    /* 
    public void createBaseUser(){

    }
    */
    //needs transactions
    //clean up json objects
    //prevent redundancies
    public void createUser(String username, String firstName,
                                //String middleName
                                String lastName, 
                                    int contacts, String email, boolean verified, String password, String accessToken,
                                        String refreshToken){
        User newUser = new User();
        //entityManager.joinTransaction();
        //newUser.setUserId(0);
        newUser.setUsername(username);
        newUser.setF_name(firstName);
        //newUser.setM_name(middleName);
        newUser.setL_Name(lastName);
        newUser.setPhone_No(contacts);
        newUser.setEmail(email);
        newUser.setVerified(verified);
        entityManager.persist(newUser);
        entityManager.flush();
        UserSecurity securityDetails = new UserSecurity();
        securityDetails.setUser(newUser);
        securityDetails.setPassword(password);
        securityDetails.setAccessTkn(accessToken);
        securityDetails.setRefreshTkn(refreshToken);
        newUser.setSecurityDetails(securityDetails);
        //entityManager.persist(newUser);
        entityManager.merge(newUser);
    }
    
    public void createUser(UserJSON userJSON){
        createUser(userJSON.getUsername(), userJSON.getFirstName(), 
                        //userJSON.getMiddleName(),
                        userJSON.getLastName(), userJSON.getContacts(), userJSON.getEmail() , userJSON.isVerified(), 
                            userJSON.getUserSecurity().getPassword(), userJSON.getUserSecurity().getAccessTkn(), 
                                userJSON.getUserSecurity().getRefreshTkn());
    }

    public void setSecurityDetails(UserSecurityJSON security){
    /*  
        User user = entityManager.find(User.class, security.getUser().getUsername());
        user.getSecurityDetails().setAccessTkn(security.getAccessTkn());
        user.getSecurityDetails().setRefreshTkn(security.getRefreshTkn());
        entityManager.merge(user);
    */
        Query query = null;
        if(security.getPassword() != null){
            query = entityManager.createNamedQuery("UserSecurity.updatePassword");
            query.setParameter("username", security.getUser().getUsername());
            query.setParameter("password", security.getPassword());
            query.setParameter("accessTkn", security.getAccessTkn());
            query.setParameter("refreshTkn", security.getRefreshTkn());
            
        }
        else{
            query = entityManager.createNamedQuery("UserSecurity.updateJsonTkns");
            query.setParameter("username", security.getUser().getUsername());
            query.setParameter("accessTkn", security.getAccessTkn());
            query.setParameter("refreshTkn", security.getRefreshTkn()); 
        }
        int result = query.executeUpdate();
        if(result == 0){
            throw new RuntimeException("Unable to update password.");
        }
    }

    public Set<User> getUserEntities(){
       try{
        Stream<User> query = entityManager.createNamedQuery("UserEntity.findAllUsers", User.class) .getResultStream();
        return query.collect(Collectors.toSet());
       } 
       catch(NoResultException ex){
        return null;
       }
    }

    //return a no result exception if user does not exist
    public UserSecurityJSON getSecurityDetails(UserJSON user){  
        Query query = null; 
        
        if(user.getUserId() != 0){
            query = entityManager.createNamedQuery("UserSecurity.findByUserId");
            query.setParameter("userId", user.getUserId());
        }
        else if(user.getUsername() != null){
            query = entityManager.createNamedQuery("UserSecurity.findByUsername");
            query.setParameter("username", user.getUsername());
        }
        else if(user.getEmail() != null){
            query = entityManager.createNamedQuery("UserSecurity.findByEmail");
            query.setParameter("email", user.getEmail());
        }
        /* 
        UserSecurityJSON security = (UserSecurityJSON) query.getSingleResult();
        UserJSON userDetails = new UserJSON(user.getUserId(), user.getUsername());
        UserSecurityJSON securityJSON = new UserSecurityJSON(security.getAccessTkn(),security.getPassword(), security.getRefreshTkn(), userDetails);
        return securityJSON;
        */

        try{
            Object[] security = (Object[])  query.getSingleResult();
            int userId = (int) security[0];
            String username = (String) security[1];
            String accessTkn = (String) security[3];
            String refreshTkn = (String) security[4];   
            UserSecurityJSON securityJSON = new UserSecurityJSON(accessTkn, null, refreshTkn, new UserJSON(userId, username));
            //UserJSON userDetails = new UserJSON(user.getUserId(), user.getUsername());
            return securityJSON;
        }
        catch(NoResultException ex){
            return null;
        }
            
    }

    public boolean emailExists(UserJSON user){
        if(user.getEmail() != null){
            Query query = entityManager.createNamedQuery("User.emailExists");
            query.setParameter("email", user.getEmail());
            Long count = (Long) query.getSingleResult();
            if(count == 1){
                return true;
            };
            return false;
        }
        else return false;
    }
    /*
    public UserJSON getUserDetails(UserJSON user){
        UserJSON secDetails = getSecurityDetails(user);     
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        return user;
    }
    */

    //return a no result exception if user does not exist
    public UserJSON getUserDetails(UserJSON user){
        Query query = null;
        if(user.getUserId()!= 0){
            query = entityManager.createNamedQuery("UserSecurity.findByEmail");
            query.setParameter("email", user.getEmail());
        }
        else if(user.getUsername()!= null){
            query = entityManager.createNamedQuery("UserSecurity.findByUsername");
            query.setParameter("username", user.getUsername());
        }
        else if(user.getEmail()!= null){
            query = entityManager.createNamedQuery("UserSecurity.findBy UserId");
            query.setParameter("userId", user.getUserId());
        }
        try{
            Object[] userDetails = (Object[]) query.getSingleResult();
            int userId = (int) userDetails[0];
            String username = (String) userDetails[1];
            String email = (String) userDetails[2];
            String accessTkn = (String) userDetails[3];
            String refreshTkn = (String) userDetails[4];
            user.setEmail(email);
            user.setUserId(userId);
            user.setUsername(username);
            user.setUserSecurity(new UserSecurityJSON(accessTkn,null,refreshTkn,new UserJSON(userId,username)));
            return user;
        }
        catch(NoResultException ex){
            return null;
        }
    }
    
    public boolean isAdmin(UserJSON user) throws NoResultException{
        if(user.getUserId() != 0){
            User userDetails = entityManager.find(User.class, user.getUserId());
            if(userDetails!= null){
                if(userDetails instanceof UserAdmin)
                    return true;
            }
        }
        else{
            Query query = entityManager.createNamedQuery("Admin.isAdminByUsername");
            query.setParameter("username", user.getUsername());
            Long count = (Long) query.getSingleResult();
            if(count == 1){
                return true;
            };
            return false;
        }

            return false;
    }

    //return a no result exception if user does not exist
    public UserSecurityJSON getUserPassword(UserSecurityJSON userSecurityDetails) throws NoResultException{
        Query query = null;
        if(userSecurityDetails.getUser().getUserId() == 0 && userSecurityDetails.getUser().getUsername() != null){
            query = entityManager.createNamedQuery("UserSecurity.getUserPasswordFromUsername");
            query.setParameter("username", userSecurityDetails.getUser().getUsername());
        }
        else
        {
            query = entityManager.createNamedQuery("UserSecurity.getUserPasswordFromUserId");
            query.setParameter("userId", userSecurityDetails.getUser().getUserId());
        }
        
        Object[] security = (Object[]) query.getSingleResult();
        int userId = (int) security[1];
        String username = (String) security[2];
        String password = (String) security[0];
        return new UserSecurityJSON(null,password,null, new UserJSON(userId, username));
    }

    public void setUserPassword(UserSecurityJSON userSecurity){
        Query query = null;
        if(userSecurity.getUser().getUserId() == 0 && userSecurity.getUser().getUsername()!= null){
            query = entityManager.createNamedQuery("UserSecurity.updatePasswordFromUsername");
            query.setParameter("username", userSecurity.getUser().getUsername());
            query.setParameter("password", userSecurity.getPassword());
        }
        else
        {
            query = entityManager.createNamedQuery("UserSecurity.updatePasswordFromUserId");
            query.setParameter("userId", userSecurity.getUser().getUserId());
            query.setParameter("password", userSecurity.getPassword());
        }
        int result = query.executeUpdate();
        if(result == 0){
            throw new RuntimeException("Unable to update password.");
        }
    }

    //commit the followings of user from category entity
    public void editUser(UserJSON user){
        if(user== null)
            return;
        User userEntity = getUserEntity(user);
        if(userEntity == null)
            return;
        userEntity.setEmail(user.getEmail());
        userEntity.setF_name(user.getFirstName());
        userEntity.setL_Name(user.getLastName());
        userEntity.setPhone_No(user.getContacts());
        userEntity.setUsername(user.getUsername());
        entityManager.merge(userEntity);
    }




    public UserJSON getUserInfo(UserJSON user){
        if(user==null)
            return null;
        User userEntity = getUserEntity(user);
        Set<TopicJSON> topicsFollowing  = getTopicFollowers(userEntity);
        Set<PostJSON> posts = getUserPosts(userEntity);
        Set<CategoryJSON> categoriesFollowing = getCategoryFollowers(userEntity);
        UserJSON userJSON = new UserJSON(userEntity.getUserId(), userEntity.getEmail(), userEntity.getUsername(),
                                userEntity.getPhone_No(),userEntity.isVerified(), userEntity.getF_name(),
                                    userEntity.getL_Name(), null,posts, 
                                        categoriesFollowing, topicsFollowing);
        return userJSON;
    }

    public Set<TopicFollower> getTopicFollowerEntities(User user){
        if(user == null)
            return null;
        Set<TopicFollower> topicsFollowed = null;
        topicsFollowed = user.getTopicFollowing();
        if(topicsFollowed == null){    
            List<TopicFollower> topics = entityManager.createNamedQuery("TopicFollower.findByUserId", TopicFollower.class)
                            .setParameter("userId", user.getUserId()).getResultList();
            return topics.stream().collect(Collectors.toSet());
        }
        return topicsFollowed;
    }

    public Set<PostJSON> getUserPosts(User  user){
        Set<Post> userPosts = getUserPostEntities(user);
        Set<PostJSON> postJSONs = new HashSet<PostJSON>();
        for(Post p : userPosts){
            postJSONs.add(new PostJSON(p.getPost_Id(), null, null, 0,0,null));
        }
        return postJSONs;
    }

    public Set<Post> getUserPostEntities(User user){
        if(user == null)
            return null;
        Set<Post> userPosts = null;
        userPosts = user.getPosts();
        if(userPosts == null){    
            List<Post> posts = entityManager.createNamedQuery("Post.findByUserId", Post.class)
                            .setParameter("userId", user.getUserId()).getResultList();
            return posts.stream().collect(Collectors.toSet());
        }
        return userPosts;
    }


    public Set<TopicJSON> getTopicFollowers(User user){
        Set<TopicFollower> topicFollowers = getTopicFollowerEntities(user);
        Set<TopicJSON> topicJSONs = new HashSet<TopicJSON>();
        for(TopicFollower tf : topicFollowers){
            topicJSONs.add(new TopicJSON(tf.getTopic_Id(), null, null, null, null,null));
        }
        return topicJSONs;
    }

    public Set<CategoryFollower> getCategoryFollowerEntities(User user){
        if(user == null)
            return null;
        Set<CategoryFollower> categoryFollowers = null;
        categoryFollowers = user.getCategoriesFollowing();
        if(categoryFollowers == null){    
            List<CategoryFollower> topics  = entityManager.createNamedQuery("TopicFollower.findByUserId", CategoryFollower.class)
                .setParameter("userId", user.getUserId()).getResultList();
            return topics.stream().collect(Collectors.toSet());
        }
        return categoryFollowers;
    }

    public Set<CategoryJSON> getCategoryFollowers(User user){
        Set<CategoryFollower> topicFollowers = getCategoryFollowerEntities(user);
        Set<CategoryJSON> categJSONs = new HashSet<CategoryJSON>();
        for(CategoryFollower tf : topicFollowers){
            categJSONs.add(new CategoryJSON(tf.getCat_Id(), null, null, null, null));
        }
        return categJSONs;
    }

    public User getUserEntity(UserJSON user){
        if(user.getUserId() == 0 && user.getUsername() == null)
            return null;
        User userEntity = null;
        if(user.getUserId() != 0){
            userEntity = entityManager.find(User.class, user.getUserId());
        }
        else{
            try{
                Query query = entityManager.createNamedQuery("User.findUsersByUsername");
                query.setParameter("username", user.getUsername());
                userEntity = (User) query.getSingleResult();
            }
            catch(NoResultException ex){
                return null;
            }
        }
        return userEntity;
    }

    public Set<UserJSON> searchByUsername(UserJSON userJSON){
        if(userJSON  != null &&  userJSON.getUsername() != null ){
            List<User> users = entityManager.createNamedQuery("User.searchByUsername", User.class)
                                .setParameter("username", "%" + userJSON.getUsername() + "%").getResultList();
            return users.stream().map(user ->{ 
                                UserJSON userInst  = new UserJSON(user.getUserId(), user.getUsername());
                                    return userInst;
                    }).collect(Collectors.toSet());
            }
        else{
            return null;
        }
    }

    public void deleteUser(UserJSON user){
        User userEntity = getUserEntity(user);
        if(userEntity == null)
            return;
        entityManager.remove(userEntity);
        entityManager.remove(userEntity.getSecurityDetails());
        entityManager.flush();
    }

    public Set<UserJSON> getFollowers(UserJSON user){
        try{
            User userEntity = getUserEntity(user);
            Set<UserJSON>  following = userEntity.getUserFollowedBy().stream().map(userEntityInst -> {
                                            UserJSON userInst = new UserJSON(userEntityInst.getFollower().getUsername());
                                            return userInst;
                        
            }).collect(Collectors.toSet());
            return following;
        }
        catch(NoResultException ex){
            return null;
        }
    }

    public Set<UserJSON> getFollowing(UserJSON user){
        try{
            User userEntity = getUserEntity(user);
            Set<UserJSON>  following = userEntity.getUserFollowing().stream().map(userEntityInst -> {
                                            UserJSON userInst = new UserJSON(userEntityInst.getUser().getUsername());
                                            return userInst;
                        
            }).collect(Collectors.toSet());
            return following;
        }
        catch(NoResultException ex){
            return null;
        }
    }

}

