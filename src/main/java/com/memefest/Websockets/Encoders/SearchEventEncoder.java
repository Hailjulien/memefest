package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.memefest.Websockets.JSON.SearchEventJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class SearchEventEncoder implements Encoder.Text<SearchEventJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(SearchEventJSON userSecurity) {
    try {
      return this.mapper.writeValueAsString(userSecurity);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}

