package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetTopicFollowNotificationJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetTopicFollowNotificationDecoder implements Decoder.Text<GetTopicFollowNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetTopicFollowNotificationJSON decode(String text) {
    try {
      return (GetTopicFollowNotificationJSON)this.mapper.readValue(text, GetTopicFollowNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetTopicFollowNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}