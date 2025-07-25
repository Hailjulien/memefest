package com.memefest.Websockets.Encoders;



import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.memefest.DataAccess.JSON.AdminJSON;
import com.memefest.DataAccess.JSON.EventJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class EventEncoder implements Encoder.Text<EventJSON> {
  private ObjectMapper mapper = new ObjectMapper();
  
  @Override
  public String encode(EventJSON admin) {
    try {     Map<String, SimpleBeanPropertyFilter> filterMap = new HashMap<String, SimpleBeanPropertyFilter> ();
      
      SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.serializeAllExcept("posts","contacts",
                                                                              "firstName","lastName","userSecurity", 
                                                                        "topicsFollowing", "categoriesFollowing");
      filterMap.put("UserPublicView", userFilter);
      filterMap.put("EventPublicView", eventPublicView());
      FilterProvider filterProvider =new  SimpleFilterProvider().addFilter("UserPublicView", userFilter)
                                          .addFilter("EventPublicView", eventPublicView());
      return mapper.writer(filterProvider).writeValueAsString(admin);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }

    protected SimpleBeanPropertyFilter eventPublicView(){
    SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("posts", "clips", "posters");
    return filter;
  }

  
  public void init(EndpointConfig conf) {}
  
  public void destroy() {}
}
