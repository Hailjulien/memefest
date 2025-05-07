package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.Websockets.JSON.GetUserFollowNotificationJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class GetUserFollowNotificationDecoder implements Decoder.Text<GetUserFollowNotificationJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public GetUserFollowNotificationJSON decode(String text) {
    try {
      return (GetUserFollowNotificationJSON)this.mapper.readValue(text, GetUserFollowNotificationJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    /*InteractNotificationJSON result = decode(text);
    if (result != null)
      return true; 
    return false;
    */
    //return this.mapper.canSerialize(InteractNotificationJSON.class);
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(GetUserFollowNotificationJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
