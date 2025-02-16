package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.CategoryJSON;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class CategoryDecoder implements Decoder.Text<CategoryJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public CategoryJSON decode(String text) {
    try {
      return (CategoryJSON)this.mapper.readValue(text, CategoryJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    CategoryJSON result = decode(text);
    if (result != null)
      return true; 
    return false;
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
