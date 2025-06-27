package com.memefest.Websockets.JSON;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Getable {
    POST("POST"), CATEGORY("CATEGORY"), TOPIC("TOPIC"), USER("USER"), EVENT("EVENT"), REPOST("REPOST"), NOTIFICATION("NOTIFICATION");

    
    @JsonProperty("GetType")
    private String valueString;

    private int value; 

    Getable(String type){
        switch (type.toUpperCase()) {
            case ("POST"):
                this.value=1;
                this.valueString = type;
                break;
            case "CATEGORY":
                this.value = 1;
                this.valueString = type;
                break;
            case "USER":{
                this.valueString = type;
                this.value = 4;
                break;
            }
            case "EVENT":{
                this.valueString = type;
                this.value = 5;
                break;
            }
            case "REPOST":{
                this.valueString = type;
                this.value = 6;
                break;
            }
            case "NOTIFICATION":{
                this.valueString = type;
                this.value = 7;
                break;
            }
            default:
                break;
        }
    }

    @JsonCreator
    public static Getable forValues(@JsonProperty("GetType") String type){
        Getable editable = null;
        switch(type.toUpperCase()){
                case "POST":{
                    editable = Getable.POST;
                }
                case "CATEGORY":{
                    editable = Getable.CATEGORY;
                }
                case "TOPIC" :{
                    editable = Getable.TOPIC;
                }
                case "USER":{
                   editable = Getable.USER;
                }
                case "EVENT":{
                    editable = Getable.EVENT;
                }
                case "REPOST":{
                    editable = Getable.REPOST;
                }
                case "NOTIFICATION":{
                    editable = Getable.NOTIFICATION;
                }
            }
        return editable;
    }
    
    @JsonValue
    public int getValue(){
        return this.value;
    }
}