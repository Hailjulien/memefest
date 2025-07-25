package com.memefest.Websockets.Encoders;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.memefest.Websockets.JSON.GetResultPostJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
    
public class GetResultPostEncoder extends ContentEncoder implements Encoder.Text<GetResultPostJSON>{
        
    
    @Override
    public String encode(GetResultPostJSON category) {
        Map<String,SimpleBeanPropertyFilter> filters = new HashMap<String, SimpleBeanPropertyFilter>();
        filters.put("UserPublicView", userPublicViewFilter());
        FilterProvider filtersProvider = setFilters(filters);
        try{
            return mapper.writer(filtersProvider).writeValueAsString(category);            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void init(EndpointConfig conf){
    
    }
    
    @Override
    public void destroy(){

    }    
}
