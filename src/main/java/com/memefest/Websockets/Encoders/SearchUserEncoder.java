package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.SearchUserJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class SearchUserEncoder implements Encoder.Text<SearchUserJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(SearchUserJSON userSecurity) {
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
