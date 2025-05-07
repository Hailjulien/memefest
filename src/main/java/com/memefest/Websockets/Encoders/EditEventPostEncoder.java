package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.Websockets.JSON.EditEventPostJSON;

import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
    
public class EditEventPostEncoder implements Encoder.Text<EditEventPostJSON>{
        
    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public String encode(EditEventPostJSON category) {
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
