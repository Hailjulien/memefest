package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.UserJSON;

@JsonRootName("GetUser")
public class GetUserJSON  extends GetJSON {
    
    @JsonProperty("Users")
    private  Set<UserJSON> users;

    @JsonCreator
    public GetUserJSON(@JsonProperty("Users") Set<UserJSON> users){
        super(Getable.USER);
        this.users = users;
    }

    public void setUsers(Set<UserJSON> users){
        this.users = users;
    }

    public Set<UserJSON> getUsers(){
        return this.users;
    }
}
