package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditResultTopicJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditResultTopicDecoder implements Decoder.Text<EditResultTopicJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditResultTopicJSON decode(String text) {
    try {
      return (EditResultTopicJSON)this.mapper.readValue(text, EditResultTopicJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditResultTopicJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
