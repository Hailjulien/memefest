package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.DataAccess.JSON.SubCategoryJSON;
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
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(SubCategoryJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
