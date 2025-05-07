package com.memefest.Websockets.Encoders;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.GetResultTopicPostJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
    
public class GetResultTopicPostEncoder implements Encoder.Text<GetResultTopicPostJSON>{
        
    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public String encode(GetResultTopicPostJSON category) {
        try{
            return mapper.writeValueAsString(category);            
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
