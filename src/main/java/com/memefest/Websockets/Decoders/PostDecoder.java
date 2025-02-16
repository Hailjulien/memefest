package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.PostJSON;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class PostDecoder implements Decoder.Text<PostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public PostJSON decode(String text) {
    try {
      return (PostJSON)this.mapper.readValue(text, PostJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    PostJSON result = decode(text);
    if (result != null)
      return true; 
    return false;
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
