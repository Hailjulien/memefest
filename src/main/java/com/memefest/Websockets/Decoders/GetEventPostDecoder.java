package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetEventPostJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetEventPostDecoder implements Decoder.Text<GetEventPostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetEventPostJSON decode(String text) {
    try {
      return (GetEventPostJSON)this.mapper.readValue(text, GetEventPostJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetEventPostJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}

