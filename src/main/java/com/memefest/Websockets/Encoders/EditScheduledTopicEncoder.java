package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.EditResultRepostJSON;
import com.memefest.Websockets.JSON.EditResultScheduledEventJSON;
import com.memefest.Websockets.JSON.EditResultScheduledTopicJSON;
import com.memefest.Websockets.JSON.EditScheduledEventJSON;
import com.memefest.Websockets.JSON.EditScheduledTopicJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EditScheduledTopicEncoder implements Encoder.Text<EditScheduledTopicJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(EditScheduledTopicJSON userSecurity) {
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