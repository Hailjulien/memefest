package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.GetCategoryJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class GetCategoryEncoder implements Encoder.Text<GetCategoryJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(GetCategoryJSON userSecurity) {
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
