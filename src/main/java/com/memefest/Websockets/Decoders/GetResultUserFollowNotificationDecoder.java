package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetResultUserFollowNotificationJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetResultUserFollowNotificationDecoder implements Decoder.Text<GetResultUserFollowNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetResultUserFollowNotificationJSON decode(String text) {
    try {
      return (GetResultUserFollowNotificationJSON)this.mapper.readValue(text, GetResultUserFollowNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetResultUserFollowNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}