package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetEventPostNotificationJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetEventPostNotificationDecoder implements Decoder.Text<GetEventPostNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetEventPostNotificationJSON decode(String text) {
    try {
      return (GetEventPostNotificationJSON)this.mapper.readValue(text, GetEventPostNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetEventPostNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}