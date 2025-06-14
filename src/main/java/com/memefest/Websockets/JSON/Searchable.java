package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Searchable {
    TOPIC, CATEGORY, POST, USER, EVENT;

    
    @JsonCreator
    public static Searchable forValues(@JsonProperty("SearchType") String Type){
        for(Searchable searchable : Searchable.values()){
            if(searchable.name().equalsIgnoreCase(Type)){
                return searchable;
            }
        }
        throw new IllegalArgumentException("Invalid SearchType: " + Type);
    }

    public String getValueString(){
        return this.name().toLowerCase();
    }
}
