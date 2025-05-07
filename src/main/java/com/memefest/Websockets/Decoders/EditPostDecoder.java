package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditPostJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditPostDecoder implements Decoder.Text<EditPostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditPostJSON decode(String text) {
    try {
      return (EditPostJSON)this.mapper.readValue(text, EditPostJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
 return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditPostJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
