package com.memefest.Websockets.Decoders;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditAdminJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditAdminDecoder implements Decoder.Text<EditAdminJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditAdminJSON decode(String text) {
    try {
      return (EditAdminJSON)this.mapper.readValue(text, EditAdminJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditAdminJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
