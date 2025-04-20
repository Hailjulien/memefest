package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class PostWithReplyEncoder implements Encoder.Text<PostWithReplyJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public String encode(PostWithReplyJSON postWithReply) {
    try {
      return this.mapper.writeValueAsString(postWithReply);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}