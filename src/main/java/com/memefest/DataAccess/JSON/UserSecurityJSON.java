package com.memefest.DataAccess.JSON;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("UserSecurity")
public class UserSecurityJSON {
  @JsonBackReference("userSecurity")
  @JsonProperty("User")
  private UserJSON user;
  
  @JsonProperty("AccessToken")
  private String accessTkn;
  
  @JsonProperty("Password")
  private String password;
  
  @JsonProperty("RefreshToken")
  private String refreshTkn;
  
  @JsonProperty("Cancel")
  private boolean canceled;
  
  public UserSecurityJSON() {}
  
  public UserSecurityJSON(@JsonProperty("AccessToken") String accessToken, @JsonProperty("Password") String password, @JsonProperty("RefreshToken") String refreshToken) {
    this.accessTkn = accessToken;
    this.password = password;
    this.refreshTkn = refreshToken;
    this.user = null;
    this.canceled = false;
  }
  
  public UserSecurityJSON(@JsonProperty("Password") String password, @JsonProperty("UserId") int userId, @JsonProperty("Username") String username) {
    this.password = password;
    this.user = new UserJSON(userId, username);
    this.refreshTkn = null;
    this.accessTkn = null;
    this.canceled = false;
  }
  
  @JsonCreator
  public UserSecurityJSON(@JsonProperty("AccessToken") String accessTkn, @JsonProperty("Password") String password, @JsonProperty("RefreshToken") String refreshToken, @JsonProperty("UserId") int userId, @JsonProperty("Username") String username) {
    this(accessTkn, password, refreshToken, new UserJSON(userId, username));
  }
  
  public UserSecurityJSON(@JsonProperty("AccessToken") String accessTkn, @JsonProperty("Password") String password, @JsonProperty("RefreshToken") String refreshTkn, UserJSON user) {
    this.accessTkn = accessTkn;
    this.password = password;
    this.refreshTkn = refreshTkn;
    this.user = new UserJSON(user.getUserId(), user.getUsername());
    this.canceled = false;
  }
  
  @JsonProperty("User")
  public UserJSON getUser() {
    return this.user;
  }
  
  @JsonProperty("User")
  public void setUser(UserJSON user) {
    this.user = user;
  }
  
  @JsonProperty("AccessToken")
  public String getAccessTkn() {
    return this.accessTkn;
  }
  
  @JsonProperty("AccessToken")
  public void setAccessTkn(String accessTkn) {
    this.accessTkn = accessTkn;
  }
  
  @JsonProperty("Cancel")
  public boolean isCancelled() {
    return this.canceled;
  }
  
  @JsonProperty("Cancel")
  public void setCanceled(boolean canceled) {
    this.canceled = canceled;
  }
  
  @JsonProperty("Password")
  public String getPassword() {
    return this.password;
  }
  
  @JsonProperty("Password")
  public void setPassword(String password) {
    this.password = password;
  }
  
  @JsonProperty("RefreshToken")
  public String getRefreshTkn() {
    return this.refreshTkn;
  }
  
  @JsonProperty("RefreshToken")
  public void setRefreshTkn(String refreshTkn) {
    this.refreshTkn = refreshTkn;
  }
}
