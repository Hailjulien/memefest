package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.GetResultEventJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
    
public class GetResultEventEncoder implements Encoder.Text<GetResultEventJSON>{
        
    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public String encode(GetResultEventJSON category) {
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
