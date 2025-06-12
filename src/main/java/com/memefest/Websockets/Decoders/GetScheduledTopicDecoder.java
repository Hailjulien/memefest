package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetResultRepostJSON;
import com.memefest.Websockets.JSON.GetResultScheduledEventJSON;
import com.memefest.Websockets.JSON.GetResultScheduledTopicJSON;
import com.memefest.Websockets.JSON.GetScheduledEventJSON;
import com.memefest.Websockets.JSON.GetScheduledTopicJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetScheduledTopicDecoder implements Decoder.Text<GetScheduledTopicJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetScheduledTopicJSON decode(String text) {
    try {
      return (GetScheduledTopicJSON)this.mapper.readValue(text, GetScheduledTopicJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetScheduledTopicJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}