package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "EditId")
public abstract class EditJSON {
    @JsonProperty("EditableType")
    private Editable editable;

    @JsonProperty("EditId")
    private int editId;

    @JsonCreator
    public EditJSON(@JsonProperty("EditableType") Editable editable){
        this.editable = editable;
    }
    
    public Editable getEditable(){
        return editable;
    }
    
    public int getEditId(){
        return this.editId;
    }

    public void setEditId(int editId){
        this.editId = editId;
    }

}
