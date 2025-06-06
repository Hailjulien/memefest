package com.memefest.Websockets.Decoders;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditResultEventJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditResultEventPostDecoder implements Decoder.Text<EditResultEventJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditResultEventJSON decode(String text) {
    try {
      return (EditResultEventJSON)this.mapper.readValue(text, EditResultEventJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditResultEventJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}