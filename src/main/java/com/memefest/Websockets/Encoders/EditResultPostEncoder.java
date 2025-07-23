package com.memefest.Websockets.Encoders;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.memefest.Websockets.JSON.EditResultPostJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EditResultPostEncoder extends ContentEncoder implements Encoder.Text<EditResultPostJSON>{
 
  public String encode(EditResultPostJSON userSecurity) {
    Map<String,SimpleBeanPropertyFilter> filters = new HashMap<String, SimpleBeanPropertyFilter>();
    filters.put("UserPublicView", userPublicViewFilter());
    FilterProvider filtersProvider = setFilters(filters);
    try {
      return this.mapper.writer(filtersProvider).writeValueAsString(userSecurity);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }


  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}
