package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.EditResultPostNotificationJSON;
import com.memefest.Websockets.JSON.EditResultPostWithReplyJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;


public class EditResultPostWithReplyEncoder implements Encoder.Text<EditResultPostWithReplyJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(EditResultPostWithReplyJSON userSecurity) {
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

