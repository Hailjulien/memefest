package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Editable {
    POST, CATEGORY, TOPIC, USER, EVENT, REPOST;

    @JsonCreator
    public static Editable forValues(@JsonProperty("EditableType") String Type){
        for(Editable interaction : Editable.values()){
            if(interaction.name().equalsIgnoreCase(Type)){
                return interaction;
            }
        }
        throw new IllegalArgumentException("Invalid EditableType: " + Type);
    }

    public String getValueString(){
        return this.name().toLowerCase();
    }
}
