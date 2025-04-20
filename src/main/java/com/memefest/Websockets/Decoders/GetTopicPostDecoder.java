package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.FollowNotificationJSON;
import com.memefest.Websockets.JSON.GetTopicPostJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetTopicPostDecoder implements Decoder.Text<GetTopicPostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetTopicPostJSON decode(String text) {
    try {
      return (GetTopicPostJSON)this.mapper.readValue(text, GetTopicPostJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(FollowNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}

