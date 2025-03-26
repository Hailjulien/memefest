package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.EventPostJSON;
import com.memefest.DataAccess.JSON.PostJSON;
import com.memefest.DataAccess.JSON.TopicPostJSON;
import com.memefest.DataAccess.JSON.UserSecurityJSON;
import com.memefest.Websockets.JSON.EditResultPostJSON;
import com.memefest.Websockets.JSON.GetEventJSON;
import com.memefest.Websockets.JSON.GetRepostJSON;
import com.memefest.Websockets.JSON.GetTopicJSON;
import com.memefest.Websockets.JSON.InteractNotificationJSON;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class GetEventEncoder implements Encoder.Text<GetEventJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(GetEventJSON userSecurity) {
    try {
      return this.mapper.writeValueAsString(userSecurity);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}
