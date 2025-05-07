package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.EditResultEventPostJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
    
public class EditResultEventPostEncoder implements Encoder.Text<EditResultEventPostJSON>{
        
    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public String encode(EditResultEventPostJSON category) {
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
