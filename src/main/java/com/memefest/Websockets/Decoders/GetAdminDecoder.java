package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetAdminJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetAdminDecoder implements Decoder.Text<GetAdminJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetAdminJSON decode(String text) {
    try {
      return (GetAdminJSON)this.mapper.readValue(text, GetAdminJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetAdminJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}