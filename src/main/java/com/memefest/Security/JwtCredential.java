package com.memefest.Security;

import jakarta.security.enterprise.credential.Credential;

public class JwtCredential implements Credential {
  private String jwtToken;
  
  public JwtCredential(String authenticationHeader) throws NullPointerException, IllegalArgumentException {
    this.jwtToken = authenticationHeader;
  }
  
  public String getAccessToken() {
    return this.jwtToken;
  }
}
