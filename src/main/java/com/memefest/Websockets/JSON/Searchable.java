package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Searchable {
    POST("POST"), CATEGORY("CATEGORY"), TOPIC("TOPIC"), USER("USER"), EVENT("EVENT"), REPOST("REPOST"), NOTIFICATION("NOTIFICATION");

    
    @JsonProperty("SearchType")
    private String valueString;

    private int value; 

    Searchable(String type){
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
    public static Searchable forValues(@JsonProperty("SearchType") String type){
        Searchable editable = null;
        switch(type.toUpperCase()){
                case "POST":{
                    editable = Searchable.POST;
                }
                case "CATEGORY":{
                    editable = Searchable.CATEGORY;
                }
                case "TOPIC" :{
                    editable = Searchable.TOPIC;
                }
                case "USER":{
                   editable = Searchable.USER;
                }
                case "EVENT":{
                    editable = Searchable.EVENT;
                }
                case "REPOST":{
                    editable = Searchable.REPOST;
                }
                case "NOTIFICATION":{
                    editable = Searchable.NOTIFICATION;
                }
            }
        return editable;
    }
    
    @JsonValue
    public int getValue(){
        return this.value;
    }
}