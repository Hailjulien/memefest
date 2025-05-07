package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.EditAdminJSON;
import com.memefest.Websockets.JSON.GetResultUserJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetResultUserDecoder implements Decoder.Text<GetResultUserJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetResultUserJSON decode(String text) {
    try {
      return (GetResultUserJSON)this.mapper.readValue(text, GetResultUserJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(EditAdminJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}