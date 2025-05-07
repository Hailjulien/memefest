package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.GetResultNotificationJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
    
public class GetResultNotificationEncoder implements Encoder.Text<GetResultNotificationJSON>{
        
    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public String encode(GetResultNotificationJSON category) {
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
