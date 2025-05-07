package com.memefest.Websockets.JSON;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Getable  {
    POST, CATEGORY, TOPIC, USER, EVENT, REPOST, NOTIFICATION;

    @JsonCreator
    public static Getable forValues(@JsonProperty("GetableType") String Type){
        for(Getable interaction : Getable.values()){
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
