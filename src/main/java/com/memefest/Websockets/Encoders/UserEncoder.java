package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.UserJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class UserEncoder implements Encoder.Text<UserJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(UserJSON user) {
    try {
      return this.mapper.writeValueAsString(user);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}

