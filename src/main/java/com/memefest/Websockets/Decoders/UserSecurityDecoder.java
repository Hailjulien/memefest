package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.UserSecurityJSON;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class UserSecurityDecoder implements Decoder.Text<UserSecurityJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public UserSecurityJSON decode(String text) {
    try {
      return (UserSecurityJSON)this.mapper.readValue(text, UserSecurityJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    UserSecurityJSON result = decode(text);
    if (result != null)
      return true; 
    return false;
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
