package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetRepostJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetRepostDecoder implements Decoder.Text<GetRepostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetRepostJSON decode(String text) {
    try {
      return (GetRepostJSON)this.mapper.readValue(text, GetRepostJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetRepostJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
