package com.memefest.Services;

import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.DataAccess.User;
import jakarta.ejb.Local;

@Local
public interface UserOperations {
  User getUserEntity(UserJSON paramUserJSON);
  
  UserJSON getUserInfo(UserJSON paramUserJSON);
}
