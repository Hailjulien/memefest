package com.memefest.Websockets.Encoders;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.memefest.Websockets.JSON.GetResultCategoryJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
    

public class GetResultCategoryEncoder extends ContentEncoder implements Encoder.Text<GetResultCategoryJSON>{
        
    @Override
    public String encode(GetResultCategoryJSON category) {
        
        Map<String,SimpleBeanPropertyFilter> filters = new HashMap<String, SimpleBeanPropertyFilter>();
        filters.put("UserPublicView", userPublicViewFilter());
        filters.put("TopicPublicView", topicPublicView());
        filters.put("CategoryPublicView", categoryPublicView());
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
