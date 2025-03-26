package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.Websockets.JSON.FollowNotificationJSON;
import com.memefest.Websockets.JSON.GetEventJSON;
import com.memefest.Websockets.JSON.InteractNotificationJSON;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetEventDecoder implements Decoder.Text<GetEventJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetEventJSON decode(String text) {
    try {
      return (GetEventJSON)this.mapper.readValue(text, GetEventJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
  return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetEventJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
