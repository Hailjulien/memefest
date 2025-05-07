package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetResultTopicFollowNotificationJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetResultTopicFollowNotificationDecoder implements Decoder.Text<GetResultTopicFollowNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetResultTopicFollowNotificationJSON decode(String text) {
    try {
      return (GetResultTopicFollowNotificationJSON)this.mapper.readValue(text, GetResultTopicFollowNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetResultTopicFollowNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}