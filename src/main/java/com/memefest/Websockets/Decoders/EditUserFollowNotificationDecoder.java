package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditUserFollowNotificationJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditUserFollowNotificationDecoder implements Decoder.Text<EditUserFollowNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditUserFollowNotificationJSON decode(String text) {
    try {
      return (EditUserFollowNotificationJSON)this.mapper.readValue(text, EditUserFollowNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditUserFollowNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
