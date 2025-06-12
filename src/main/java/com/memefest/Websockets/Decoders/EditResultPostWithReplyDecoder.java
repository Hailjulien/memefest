package com.memefest.Websockets.Decoders;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditResultPostNotificationJSON;
import com.memefest.Websockets.JSON.EditResultPostWithReplyJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditResultPostWithReplyDecoder implements Decoder.Text<EditResultPostWithReplyJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditResultPostWithReplyJSON decode(String text) {
    try {
      return (EditResultPostWithReplyJSON)this.mapper.readValue(text, EditResultPostWithReplyJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditResultPostWithReplyJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
