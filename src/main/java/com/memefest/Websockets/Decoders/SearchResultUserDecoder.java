package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.SearchResultUserJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class SearchResultUserDecoder implements Decoder.Text<SearchResultUserJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public SearchResultUserJSON decode(String text) {
    try {
      return (SearchResultUserJSON)this.mapper.readValue(text, SearchResultUserJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(SearchResultUserJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
