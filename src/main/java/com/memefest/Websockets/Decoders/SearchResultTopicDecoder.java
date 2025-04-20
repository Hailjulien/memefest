package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.SearchResultTopicJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class SearchResultTopicDecoder implements Decoder.Text<SearchResultTopicJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public SearchResultTopicJSON decode(String text) {
    try {
      return (SearchResultTopicJSON)this.mapper.readValue(text, SearchResultTopicJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(SearchResultTopicJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
