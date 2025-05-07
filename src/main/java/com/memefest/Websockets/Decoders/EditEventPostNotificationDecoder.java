package com.memefest.Websockets.Decoders;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditEventPostNotificationJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditEventPostNotificationDecoder implements Decoder.Text<EditEventPostNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditEventPostNotificationJSON decode(String text) {
    try {
      return (EditEventPostNotificationJSON)this.mapper.readValue(text, EditEventPostNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditEventPostNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}