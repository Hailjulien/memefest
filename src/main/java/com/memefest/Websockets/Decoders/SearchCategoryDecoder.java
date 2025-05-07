package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.SearchCategoryJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class SearchCategoryDecoder implements Decoder.Text<SearchCategoryJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public SearchCategoryJSON decode(String text) {
    try {
      return (SearchCategoryJSON)this.mapper.readValue(text, SearchCategoryJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(SearchCategoryJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
