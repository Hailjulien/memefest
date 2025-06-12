package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetResultRepostJSON;
import com.memefest.Websockets.JSON.GetResultScheduledEventJSON;
import com.memefest.Websockets.JSON.GetResultScheduledTopicJSON;
import com.memefest.Websockets.JSON.GetScheduledEventJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetScheduledEventDecoder implements Decoder.Text<GetScheduledEventJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetScheduledEventJSON decode(String text) {
    try {
      return (GetScheduledEventJSON)this.mapper.readValue(text, GetScheduledEventJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetScheduledEventJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
