package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.AdminJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class AdminEncoder implements Encoder.Text<AdminJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(AdminJSON admin) {
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
