package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditTopicFollowNotificationJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditTopicFollowNotificationDecoder implements Decoder.Text<EditTopicFollowNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditTopicFollowNotificationJSON decode(String text) {
    try {
      return (EditTopicFollowNotificationJSON)this.mapper.readValue(text, EditTopicFollowNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditTopicFollowNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}