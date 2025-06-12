package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetResultRepostJSON;
import com.memefest.Websockets.JSON.GetResultScheduledEventJSON;
import com.memefest.Websockets.JSON.GetResultScheduledTopicJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetResultScheduledTopicDecoder implements Decoder.Text<GetResultScheduledTopicJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetResultScheduledTopicJSON decode(String text) {
    try {
      return (GetResultScheduledTopicJSON)this.mapper.readValue(text, GetResultScheduledTopicJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetResultScheduledTopicJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}