package com.memefest.Websockets.Decoders;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetResultEventNotificationJSON;
import com.memefest.Websockets.JSON.GetResultEventPostJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetResultEventPostDecoder implements Decoder.Text<GetResultEventPostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetResultEventPostJSON decode(String text) {
    try {
      return (GetResultEventPostJSON)this.mapper.readValue(text, GetResultEventPostJSON.class);
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