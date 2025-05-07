package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditEventPostJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditEventPostDecoder implements Decoder.Text<EditEventPostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditEventPostJSON decode(String text) {
    try {
      return (EditEventPostJSON)this.mapper.readValue(text, EditEventPostJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditEventPostJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}