package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.SearchResultEventJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class SearchResultEventDecoder implements Decoder.Text<SearchResultEventJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public SearchResultEventJSON decode(String text) {
    try {
      return (SearchResultEventJSON)this.mapper.readValue(text, SearchResultEventJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(SearchResultEventJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}