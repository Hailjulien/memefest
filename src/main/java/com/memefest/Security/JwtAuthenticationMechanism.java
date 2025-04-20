package com.memefest.Security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
import org.glassfish.soteria.Utils;

@ApplicationScoped
public class JwtAuthenticationMechanism implements HttpAuthenticationMechanism {
  @Inject
  private IdentityStoreHandler identityStoreHandler;
  
  public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException {
    Credential credential = null;
    try {
      credential = getCredentials(request);
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    } 
    CredentialValidationResult result = null;
    if (credential instanceof UsernamePasswordCredential) {
      result = this.identityStoreHandler.validate(credential);
    } else {
      result = this.identityStoreHandler.validate(credential);
    } 
    if (result != null && result.getStatus() == CredentialValidationResult.Status.VALID)
      return context.notifyContainerAboutLogin((Principal)result.getCallerPrincipal(), result.getCallerGroups()); 
    if (context.isProtected()) {
      response.setHeader("WWW.Authenticate", String.format("Basic realm=\"%s\"", new Object[] { "Users" }));
      return context.responseUnauthorized();
    } 
    return context.doNothing();
  }
  
  public Credential getCredentials(HttpServletRequest request) throws IllegalArgumentException {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer"))
      return (Credential)new JwtCredential(authorizationHeader.substring(7)); 
    if (!Utils.isEmpty(authorizationHeader) && authorizationHeader.startsWith("Basic")) {
      String[] credentials = authorizationHeader.substring(6).split(":");
      UsernamePasswordCredential credential = new UsernamePasswordCredential(credentials[0], new Password(credentials[1]));
      return (Credential)credential;
    } 
    throw new IllegalArgumentException("Invalid or missing authorization header");
  }
}