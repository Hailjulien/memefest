package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetPostNotificationJSON;
import com.memefest.Websockets.JSON.GetPostReplysJSON;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetPostWithReplysDecoder implements Decoder.Text<GetPostReplysJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetPostReplysJSON decode(String text) {
    try {
      return (GetPostReplysJSON)this.mapper.readValue(text, GetPostReplysJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetPostReplysJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}