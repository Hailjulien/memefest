package com.memefest.Websockets.Encoders;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.memefest.Websockets.JSON.EditCategoryJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EditCategoryEncoder extends ContentEncoder implements Encoder.Text<EditCategoryJSON> {

  public String encode(EditCategoryJSON cat) {
    Map<String,SimpleBeanPropertyFilter> filters = new HashMap<String, SimpleBeanPropertyFilter>();
    filters.put("UserPublicView", userPublicViewFilter());
    filters.put("TopicPublicView", topicPublicView());
    filters.put("CategoryPublicView", categoryPublicView());
    FilterProvider filtersProvider = setFilters(filters);
    try {
      return this.mapper.writer(filtersProvider).writeValueAsString(cat);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}

  
