package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.GetTopicFollowNotificationJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class GetTopicFollowNotificationEncoder implements Encoder.Text<GetTopicFollowNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(GetTopicFollowNotificationJSON userSecurity) {
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