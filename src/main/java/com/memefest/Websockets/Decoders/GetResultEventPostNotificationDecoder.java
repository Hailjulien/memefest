package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetResultEventPostNotificationJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetResultEventPostNotificationDecoder implements Decoder.Text<GetResultEventPostNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetResultEventPostNotificationJSON decode(String text) {
    try {
      return (GetResultEventPostNotificationJSON)this.mapper.readValue(text, GetResultEventPostNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetResultEventPostNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}