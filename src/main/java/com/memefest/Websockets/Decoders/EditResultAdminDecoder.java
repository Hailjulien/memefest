package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditResultAdminJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditResultAdminDecoder implements Decoder.Text<EditResultAdminJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditResultAdminJSON decode(String text) {
    try {
      return (EditResultAdminJSON)this.mapper.readValue(text, EditResultAdminJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditResultAdminJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}