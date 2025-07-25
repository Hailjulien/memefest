package com.memefest.Websockets.Encoders;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.memefest.Websockets.JSON.EditResultTopicPostJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
    
public class EditResultTopicPostEncoder extends ContentEncoder implements Encoder.Text<EditResultTopicPostJSON>{
    
    @Override
    public String encode(EditResultTopicPostJSON category) {
        Map<String,SimpleBeanPropertyFilter> filters = new HashMap<String, SimpleBeanPropertyFilter>();
        filters.put("UserPublicView", userPublicViewFilter());
        filters.put("EventPublicView", eventPublicView());
        filters.put("TopicPublicView", topicPublicView());
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
