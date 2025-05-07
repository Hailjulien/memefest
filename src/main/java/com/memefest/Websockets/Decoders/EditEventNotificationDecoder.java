package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditEventNotificationJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditEventNotificationDecoder implements Decoder.Text<EditEventNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditEventNotificationJSON decode(String text) {
    try {
      return (EditEventNotificationJSON)this.mapper.readValue(text, EditEventNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditEventNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}