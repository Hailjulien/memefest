package com.memefest.Services;

import com.memefest.DataAccess.JSON.UserJSON;
import java.util.Set;
import com.memefest.DataAccess.User;
import jakarta.ejb.Local;

@Local
public interface UserOperations {
  
  UserJSON getUserInfo(UserJSON paramUserJSON);

  public Set<UserJSON> getFollowers(UserJSON user);

  public Set<UserJSON> getFollowing(UserJSON user);

  public void editUser(UserJSON user);

  public User getUserEntity(UserJSON user);

  public Set<UserJSON> searchByUsername(UserJSON user);
  
  public Set<User> getUserEntities();
}
