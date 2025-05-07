package com.memefest.Jaxrs.Providers;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;

public class AuthenticationDeniedExceptionMapper implements ExceptionMapper<AuthenticationDenied> {
  @Context
  private UriInfo uriInfo;
  
  public Response toResponse(AuthenticationDenied authenticationDenied) {
    Response.Status status = Response.Status.FORBIDDEN;
    return Response.status(status).entity(authenticationDenied.getMessage()).build();
  }
}
