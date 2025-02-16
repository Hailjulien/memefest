package com.memefest.Jaxrs.Providers;

public class AuthenticationDenied extends RuntimeException {
  public AuthenticationDenied(String message) {
    super(message);
  }
}
