package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditCategoryJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditCategoryDecoder implements Decoder.Text<EditCategoryJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditCategoryJSON decode(String text) {
    try {
      return (EditCategoryJSON)this.mapper.readValue(text, EditCategoryJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditCategoryJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
