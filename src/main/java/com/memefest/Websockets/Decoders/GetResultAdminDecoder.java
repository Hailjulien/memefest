package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetResultAdminJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetResultAdminDecoder implements Decoder.Text<GetResultAdminJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetResultAdminJSON decode(String text) {
    try {
      return (GetResultAdminJSON)this.mapper.readValue(text, GetResultAdminJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetResultAdminJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}