package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.InteractNotificationJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class InteractionNotificationDecoder implements Decoder.Text<InteractNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public InteractNotificationJSON decode(String text) {
    try {
      return (InteractNotificationJSON)this.mapper.readValue(text, InteractNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    /*InteractNotificationJSON result = decode(text);
    if (result != null)
      return true; 
    return false;
    */
    //return this.mapper.canSerialize(InteractNotificationJSON.class);
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(InteractNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
