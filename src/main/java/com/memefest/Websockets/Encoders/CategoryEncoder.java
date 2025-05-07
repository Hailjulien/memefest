package com.memefest.Websockets.Encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memefest.DataAccess.JSON.CategoryJSON;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
    
public class CategoryEncoder implements Encoder.Text<CategoryJSON>{
        
    private ObjectMapper mapper = new ObjectMapper();
    
    @Override
    public String encode(CategoryJSON category) {
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
