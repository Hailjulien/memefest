package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.NotificationJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class NotificationDecoder implements Decoder.Text<NotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public NotificationJSON decode(String text) {
    try {
      return (NotificationJSON)this.mapper.readValue(text, NotificationJSON.class);
    } catch (JsonProcessingException ex) {
      //ex.printStackTrace();
      return null;
    }
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(NotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
