package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetResultEventNotificationJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetResultEventNotificationDecoder implements Decoder.Text<GetResultEventNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetResultEventNotificationJSON decode(String text) {
    try {
      return (GetResultEventNotificationJSON)this.mapper.readValue(text, GetResultEventNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetResultEventNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}