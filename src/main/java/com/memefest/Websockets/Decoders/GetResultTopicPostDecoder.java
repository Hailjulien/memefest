package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.memefest.Websockets.JSON.GetResultTopicPostJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetResultTopicPostDecoder implements Decoder.Text<GetResultTopicPostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetResultTopicPostJSON decode(String text) {
    try {
      return (GetResultTopicPostJSON)this.mapper.readValue(text, GetResultTopicPostJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetResultTopicPostJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}