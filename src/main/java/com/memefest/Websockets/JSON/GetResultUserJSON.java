package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.UserJSON;

@JsonRootName("GetResultUser")
public class GetResultUserJSON  extends GetResultJSON{
    
    @JsonProperty("Users")
    private Set<UserJSON> users;

    public GetResultUserJSON(@JsonProperty("ResultCode") int resultCode,
                                @JsonProperty("ResultMessage") String resultMessage,
                                    @JsonProperty("Users") Set<UserJSON> users){
        super(Getable.USER, resultCode, resultMessage);
    }

    public void setUsers(Set<UserJSON> users){
        this.users = users;
    }

    public Set<UserJSON> getUsers(){
        return this.users;
    }

}
