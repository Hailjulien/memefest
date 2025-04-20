package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.TopicJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class TopicEncoder implements Encoder.Text<TopicJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(TopicJSON topic) {
    try {
      return this.mapper.writeValueAsString(topic);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}
