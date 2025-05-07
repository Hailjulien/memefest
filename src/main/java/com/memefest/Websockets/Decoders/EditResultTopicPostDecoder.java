package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditResultTopicPostJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class EditResultTopicPostDecoder implements Decoder.Text<EditResultTopicPostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public EditResultTopicPostJSON decode(String text) {
    try {
      return (EditResultTopicPostJSON)this.mapper.readValue(text, EditResultTopicPostJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditResultTopicPostJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}