package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.SubCategoryJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class SubCategoryEncoder implements Encoder.Text<SubCategoryJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(SubCategoryJSON category) {
    try {
      return this.mapper.writeValueAsString(category);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}
