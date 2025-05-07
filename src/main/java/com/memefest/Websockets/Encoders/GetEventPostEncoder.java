package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.EventPostJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class GetEventPostEncoder implements Encoder.Text<EventPostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(EventPostJSON userSecurity) {
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
