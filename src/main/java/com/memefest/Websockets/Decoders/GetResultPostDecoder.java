package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetResultPostJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetResultPostDecoder implements Decoder.Text<GetResultPostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetResultPostJSON decode(String text) {
    try {
      return (GetResultPostJSON)this.mapper.readValue(text, GetResultPostJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetResultPostJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}