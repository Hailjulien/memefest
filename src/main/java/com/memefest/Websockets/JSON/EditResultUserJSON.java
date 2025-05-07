package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.UserJSON;

@JsonRootName("EditResultUser")
public class EditResultUserJSON  extends EditResultJSON{
    
    @JsonProperty("Users")
    private Set<UserJSON> users;

    @JsonCreator
    public EditResultUserJSON(@JsonProperty("ResultCode") int resultCode, 
                                @JsonProperty("ResultMessage") String resultMessage,
                                @JsonProperty("Users") Set<UserJSON> users){
        super(Editable.USER, resultCode, resultMessage);
        this.users = users;
    }

    public Set<UserJSON> getUsers(){
        return this.users;
    }

    public void setUsers(Set<UserJSON> users){
        this.users = users;
    }
}
