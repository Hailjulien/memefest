package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("GetResult")
public class GetResultJSON  extends ResultJSON {
    
    @JsonProperty("Getable")
    private Getable getable;

    @JsonCreator
    public GetResultJSON(@JsonProperty("Getable") Getable getable, 
                            @JsonProperty("ResultCode") int resultCode,
                                @JsonProperty("ResultMessage") String resultMessage){
        super(resultCode, resultMessage);
        this.getable = getable;
    }

    public Getable getGetable(){
        return this.getable;
    }

}
