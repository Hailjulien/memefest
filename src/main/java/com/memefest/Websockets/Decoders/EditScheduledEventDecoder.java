package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditResultUserJSON;
import com.memefest.Websockets.JSON.EditScheduledEventJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditScheduledEventDecoder implements Decoder.Text<EditScheduledEventJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditScheduledEventJSON decode(String text) {
    try {
      return (EditScheduledEventJSON)this.mapper.readValue(text, EditScheduledEventJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditScheduledEventJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}