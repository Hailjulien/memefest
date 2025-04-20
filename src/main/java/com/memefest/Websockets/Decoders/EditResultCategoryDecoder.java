package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditResultCategoryJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditResultCategoryDecoder implements Decoder.Text<EditResultCategoryJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditResultCategoryJSON decode(String text) {
    try {
      return (EditResultCategoryJSON)this.mapper.readValue(text, EditResultCategoryJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditResultCategoryJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
