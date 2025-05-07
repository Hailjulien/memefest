    package com.memefest.Websockets.JSON;   

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "GetId")
public abstract class GetJSON {
    @JsonProperty("GetType")
    private Getable getable;

    @JsonProperty("GetId")
    private int getId;

    @JsonCreator
    public GetJSON(@JsonProperty("GetType") Getable getable){
        this.getable = getable;
    }
    
    public Getable getEditable(){
        return getable;
    }
    
    public int getGetId(){
        return this.getId;
    }

    public void setGetId(int getId){
        this.getId = getId;
    }

}
