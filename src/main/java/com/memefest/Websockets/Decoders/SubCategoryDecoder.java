package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.SubCategoryJSON;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class SubCategoryDecoder implements Decoder.Text<SubCategoryJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public SubCategoryJSON decode(String text) {
    try {
      return (SubCategoryJSON)this.mapper.readValue(text, SubCategoryJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    SubCategoryJSON result = decode(text);
    if (result != null)
      return true; 
    return false;
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
