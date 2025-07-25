package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.DataAccess.JSON.AdminJSON;
import com.memefest.DataAccess.JSON.EventJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;


public class EventDecoder implements Decoder.Text<EventJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EventJSON decode(String text) {
    try {
      return (EventJSON)this.mapper.readValue(text, EventJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EventJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
