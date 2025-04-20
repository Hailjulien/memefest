package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.PostJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class PostEncoder implements Encoder.Text<PostJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(PostJSON post) {
    try {
      return this.mapper.writeValueAsString(post);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}
