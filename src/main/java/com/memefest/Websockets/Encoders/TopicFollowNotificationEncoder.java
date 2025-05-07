package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.TopicFollowNotificationJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class TopicFollowNotificationEncoder implements Encoder.Text<TopicFollowNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(TopicFollowNotificationJSON topic) {
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
