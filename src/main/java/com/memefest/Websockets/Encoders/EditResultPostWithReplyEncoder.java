package com.memefest.Websockets.Encoders;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.memefest.Websockets.JSON.EditResultPostNotificationJSON;
import com.memefest.Websockets.JSON.EditResultPostWithReplyJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;


public class EditResultPostWithReplyEncoder extends ContentEncoder implements Encoder.Text<EditResultPostWithReplyJSON> {
  
  public String encode(EditResultPostWithReplyJSON userSecurity) {
    Map<String, SimpleBeanPropertyFilter> filterMap = new HashMap<String, SimpleBeanPropertyFilter> ();
    filterMap.put("UserPublicView", userPublicViewFilter());
    filterMap.put("TopicPublicView",topicPublicView());
    filterMap.put("CategoryPublicView", categoryPublicView());
    FilterProvider filterProvider = setFilters(filterMap); 
    try {
      return this.mapper.writer(filterProvider).writeValueAsString(userSecurity);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}

