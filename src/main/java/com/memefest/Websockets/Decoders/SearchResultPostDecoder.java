package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.SearchResultPostJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class SearchResultPostDecoder implements Decoder.Text<SearchResultPostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public SearchResultPostJSON decode(String text) {
    try {
      return (SearchResultPostJSON)this.mapper.readValue(text, SearchResultPostJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
 return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(SearchResultPostJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
