package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.DataAccess.JSON.TopicJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class TopicDecoder implements Decoder.Text<TopicJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public TopicJSON decode(String text) {
    try {
      return (TopicJSON)this.mapper.readValue(text, TopicJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(TopicJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
