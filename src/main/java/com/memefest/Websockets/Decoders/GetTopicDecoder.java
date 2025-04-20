package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetTopicJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetTopicDecoder implements Decoder.Text<GetTopicJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetTopicJSON decode(String text) {
    try {
      return (GetTopicJSON)this.mapper.readValue(text, GetTopicJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetTopicJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}

