package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditResultPostJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditResultPostDecoder implements Decoder.Text<EditResultPostJSON> {
  private ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);
  
  public EditResultPostJSON decode(String text) {
    try {
      return (EditResultPostJSON)this.mapper.readValue(text, EditResultPostJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditResultPostJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
