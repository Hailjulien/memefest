package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetResultNotificationJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetResultNotificationDecoder implements Decoder.Text<GetResultNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetResultNotificationJSON decode(String text) {
    try {
      return (GetResultNotificationJSON)this.mapper.readValue(text, GetResultNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetResultNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}