package com.memefest.Websockets.Encoders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.UserSecurityJSON;
import com.memefest.Websockets.JSON.EditPostJSON;
import com.memefest.Websockets.JSON.EditRepostJSON;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EditRepostEncoder implements Encoder.Text<EditRepostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(EditRepostJSON userSecurity) {
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

