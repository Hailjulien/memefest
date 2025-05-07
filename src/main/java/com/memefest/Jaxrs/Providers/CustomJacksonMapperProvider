package com.memefest.Jaxrs.Providers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomJacksonMapperProvider implements ContextResolver<ObjectMapper> {
  final ObjectMapper mapper;
  
  public CustomJacksonMapperProvider() {
    this.mapper = new ObjectMapper();
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }
  
  public ObjectMapper getContext(Class<?> type) {
    return this.mapper;
  }
}
