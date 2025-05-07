package com.memefest.Websockets.Encoders;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.GetAdminJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
    
public class GetAdminEncoder implements Encoder.Text<GetAdminJSON>{
        
    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public String encode(GetAdminJSON category) {
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
