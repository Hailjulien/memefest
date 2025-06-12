package com.memefest.Websockets.Encoders;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.AdminJSON;
import com.memefest.DataAccess.JSON.EventJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EventEncoder implements Encoder.Text<EventJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(EventJSON admin) {
    try {
      return this.mapper.writeValueAsString(admin);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}
