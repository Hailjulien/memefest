package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.EditResultRepostJSON;
import com.memefest.Websockets.JSON.EditResultScheduledEventJSON;
import com.memefest.Websockets.JSON.EditResultScheduledTopicJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EditResultScheduledTopicEncoder implements Encoder.Text<EditResultScheduledTopicJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(EditResultScheduledTopicJSON userSecurity) {
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

