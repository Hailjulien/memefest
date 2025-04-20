package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.TopicFollowNotificationJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class TopicFollowNotificationDecoder implements Decoder.Text<TopicFollowNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public TopicFollowNotificationJSON decode(String text) {
    try {
      return (TopicFollowNotificationJSON)this.mapper.readValue(text, TopicFollowNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
     return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(TopicFollowNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
