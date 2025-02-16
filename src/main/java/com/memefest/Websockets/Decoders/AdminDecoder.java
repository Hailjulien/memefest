package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.AdminJSON;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class AdminDecoder implements Decoder.Text<AdminJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public AdminJSON decode(String text) {
    try {
      return (AdminJSON)this.mapper.readValue(text, AdminJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    AdminJSON result = decode(text);
    if (result != null)
      return true; 
    return false;
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
