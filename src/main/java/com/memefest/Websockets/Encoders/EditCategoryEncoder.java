package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.memefest.Websockets.JSON.EditCategoryJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EditCategoryEncoder implements Encoder.Text<EditCategoryJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(EditCategoryJSON cat) {
    try {
      return this.mapper.writeValueAsString(cat);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}

  
