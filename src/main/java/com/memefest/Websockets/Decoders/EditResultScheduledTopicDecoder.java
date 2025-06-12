package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditResultRepostJSON;
import com.memefest.Websockets.JSON.EditScheduledTopicJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditResultScheduledTopicDecoder implements Decoder.Text<EditScheduledTopicJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditScheduledTopicJSON decode(String text) {
    try {
      return (EditScheduledTopicJSON)this.mapper.readValue(text, EditScheduledTopicJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditScheduledTopicJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
