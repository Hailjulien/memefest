package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetResultAdminJSON;
import com.memefest.Websockets.JSON.GetResultCategoryJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetResultCategoryDecoder implements Decoder.Text<GetResultCategoryJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetResultCategoryJSON decode(String text) {
    try {
      return (GetResultCategoryJSON)this.mapper.readValue(text, GetResultCategoryJSON.class);
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