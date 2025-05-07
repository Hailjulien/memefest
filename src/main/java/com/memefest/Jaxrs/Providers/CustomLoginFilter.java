package com.memefest.Jaxrs.Providers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Provider
@PreMatching
public class CustomLoginFilter implements ContainerRequestFilter {
  @Context
  private HttpServletRequest request;
  
  @Context
  private HttpServletResponse response;
  
  @Context
  private ResourceInfo resourceInfo;
  
  public void filter(ContainerRequestContext context) throws IOException {
    if (this.request.getHeader("Authorization") != null) {
      String authHeader = this.request.getHeader("Authorization");
      if (authHeader.startsWith("Basic")) {
        String path = this.request.getRequestURI();
        if (path.contains("SignIn/login"))
          return; 
        try {
          context.abortWith(Response.temporaryRedirect(new URI("SignIn/login")).build());
        } catch (URISyntaxException ex) {
          ex.printStackTrace();
        } 
      } 
    } 
  }
}
