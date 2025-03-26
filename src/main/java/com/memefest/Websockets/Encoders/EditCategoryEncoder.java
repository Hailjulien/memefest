package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.UserSecurityJSON;
import com.memefest.Websockets.JSON.EditCategoryJSON;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EditCategoryEncoder implements Encoder.Text<EditCategoryJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(EditCategoryJSON userSecurity) {
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

  
