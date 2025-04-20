package com.memefest.Websockets.Decoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.memefest.DataAccess.JSON.PostWithReplyJSON;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class PostWithReplyDecoder implements Decoder.Text<PostWithReplyJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  public PostWithReplyJSON decode(String text) {
    try {
      return (PostWithReplyJSON)this.mapper.readValue(text, PostWithReplyJSON.class);
    } catch (JsonProcessingException ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public boolean willDecode(String text) {
    return this.mapper.canDeserialize(TypeFactory.defaultInstance().constructType(PostWithReplyJSON.class));
  }
  
  public void init(EndpointConfig config) {
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
