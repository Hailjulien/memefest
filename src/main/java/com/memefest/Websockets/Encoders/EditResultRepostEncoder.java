package com.memefest.Websockets.Encoders;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.memefest.Websockets.JSON.EditResultRepostJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EditResultRepostEncoder extends ContentEncoder implements Encoder.Text<EditResultRepostJSON> {
  
  public String encode(EditResultRepostJSON userSecurity) {
    try {
      Map<String, SimpleBeanPropertyFilter> filterMap = new HashMap<String, SimpleBeanPropertyFilter> ();
      filterMap.put("UserPublicView", userPublicViewFilter());
      FilterProvider filterProvider = setFilters(filterMap); 
      return mapper.writer(filterProvider).writeValueAsString(userSecurity);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}

