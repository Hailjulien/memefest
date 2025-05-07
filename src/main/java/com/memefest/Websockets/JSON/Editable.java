package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


public enum Editable {
    POST("POST"), CATEGORY("CATEGORY"), TOPIC("TOPIC"), USER("USER"), EVENT("EVENT"), REPOST("REPOST"), NOTIFICATION("NOTIFICATION");

    
    @JsonProperty("EditableType")
    private String valueString;

    private int value; 

    Editable(String type){
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
    public static Editable forValues(@JsonProperty("EditableType") String type){
        Editable editable = null;
        switch(type.toUpperCase()){
                case "POST":{
                    editable = Editable.POST;
                }
                case "CATEGORY":{
                    editable = Editable.CATEGORY;
                }
                case "TOPIC" :{
                    editable = Editable.TOPIC;
                }
                case "USER":{
                   editable = Editable.USER;
                }
                case "EVENT":{
                    editable = Editable.EVENT;
                }
                case "REPOST":{
                    editable = Editable.REPOST;
                }
                case "NOTIFICATION":{
                    editable = Editable.NOTIFICATION;
                }
            }
        return editable;
    }
    
    @JsonValue
    public int getValue(){
        return this.value;
    }
}
