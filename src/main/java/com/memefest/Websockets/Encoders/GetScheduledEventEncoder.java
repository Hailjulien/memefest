package com.memefest.Websockets.Encoders;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.memefest.Websockets.JSON.GetResultUserJSON;
import com.memefest.Websockets.JSON.GetScheduledEventJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
    
public class GetScheduledEventEncoder extends ContentEncoder implements Encoder.Text<GetScheduledEventJSON>{
        
    @Override
    public String encode(GetScheduledEventJSON category) {
        Map<String, SimpleBeanPropertyFilter> filterMap = new HashMap<String, SimpleBeanPropertyFilter> ();
            filterMap.put("UserPublicView", userPublicViewFilter());
            FilterProvider filterProvider = setFilters(filterMap); 
        try{
            return mapper.writer(filterProvider).writeValueAsString(category);            
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