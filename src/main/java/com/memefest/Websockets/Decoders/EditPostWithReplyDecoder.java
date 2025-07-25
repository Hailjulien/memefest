package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditPostNotificationJSON;
import com.memefest.Websockets.JSON.EditPostWithReplyJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditPostWithReplyDecoder implements Decoder.Text<EditPostWithReplyJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditPostWithReplyJSON decode(String text) {
    try {
      return (EditPostWithReplyJSON)this.mapper.readValue(text, EditPostWithReplyJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditPostWithReplyJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}