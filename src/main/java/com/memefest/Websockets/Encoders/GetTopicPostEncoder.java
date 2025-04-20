package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.TopicPostJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class GetTopicPostEncoder implements Encoder.Text<TopicPostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(TopicPostJSON userSecurity) {
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
