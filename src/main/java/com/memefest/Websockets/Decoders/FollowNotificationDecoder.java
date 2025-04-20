package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.FollowNotificationJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class FollowNotificationDecoder implements Decoder.Text<FollowNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public FollowNotificationJSON decode(String text) {
    try {
      return (FollowNotificationJSON)this.mapper.readValue(text, FollowNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(FollowNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
