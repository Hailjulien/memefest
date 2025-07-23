package com.memefest.Websockets.Encoders;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.memefest.Websockets.JSON.GetResultPostNotificationJSON;
import com.memefest.Websockets.JSON.GetResultPostWithReplyJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
    
public class GetResultPostWithReplysEncoder extends ContentEncoder implements Encoder.Text<GetResultPostWithReplyJSON>{
        
    @Override
    public String encode(GetResultPostWithReplyJSON category) {
        try{
            Map<String, SimpleBeanPropertyFilter> filterMap = new HashMap<String, SimpleBeanPropertyFilter> ();
            filterMap.put("UserPublicView", userPublicViewFilter());
            FilterProvider filterProvider = setFilters(filterMap); 
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