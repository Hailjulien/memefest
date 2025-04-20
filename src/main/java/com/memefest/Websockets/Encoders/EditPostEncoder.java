package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.EditPostJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EditPostEncoder implements Encoder.Text<EditPostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(EditPostJSON userSecurity) {
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

