package com.memefest.Websockets.Encoders;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.memefest.DataAccess.JSON.UserJSON;
import com.memefest.Websockets.JSON.SearchResultEventJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class SearchResultEventEncoder extends ContentEncoder implements Encoder.Text<SearchResultEventJSON> {
  private ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_DEFAULT);

  @Override
  public String encode(SearchResultEventJSON userSecurity) { 
    try {
      Map<String, SimpleBeanPropertyFilter> filterMap = new HashMap<String, SimpleBeanPropertyFilter> ();
      filterMap.put("UserPublicView", userPublicViewFilter());
      filterMap.put("EventPublicView", eventPublicView());
      return mapper.writer(setFilters(filterMap)).writeValueAsString(userSecurity);
    } 
    catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }

  @Override
  public void init(EndpointConfig conf) {
    
  }
  
  @Override
  public void destroy() {}

  class PublicMixInForUser{

  }
}
