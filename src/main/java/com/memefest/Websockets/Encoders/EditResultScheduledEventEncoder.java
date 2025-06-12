package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.EditResultRepostJSON;
import com.memefest.Websockets.JSON.EditResultScheduledEventJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EditResultScheduledEventEncoder implements Encoder.Text<EditResultScheduledEventJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(EditResultScheduledEventJSON userSecurity) {
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

