package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.GetTopicJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class GetTopicEncoder implements Encoder.Text<GetTopicJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(GetTopicJSON userSecurity) {
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

