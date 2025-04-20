package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.EditEventJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EditEventEncoder implements Encoder.Text<EditEventJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(EditEventJSON event) {
    try {
      return this.mapper.writeValueAsString(event);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {

  }
}
