package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.DataAccess.JSON.UserJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class UserDecoder implements Decoder.Text<UserJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public UserJSON decode(String text) {
    try {
      return (UserJSON)this.mapper.readValue(text, UserJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
     return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(UserJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
