package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.DataAccess.JSON.UserSecurityJSON;
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
     return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(UserSecurityJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
