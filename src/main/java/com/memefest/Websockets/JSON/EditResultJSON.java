package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditResult")
public abstract class EditResultJSON extends ResultJSON{
    
    @JsonProperty("EditableType")
    private Editable  editable;
    
    @JsonCreator
    public EditResultJSON(@JsonProperty("EditableType") Editable editable, 
                            @JsonProperty("ResultCode") int resultCode,
                            @JsonProperty("ResultMessage") String resultMessage){
        super(resultCode, resultMessage);
        this.editable = editable;
    }
    
    public Editable getEditable(){
        return editable;
     }

}
