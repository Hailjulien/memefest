package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.UserJSON;

@JsonRootName("SearchUser")
public class SearchUserJSON extends SearchJSON{
    
    @JsonProperty("User")
    private UserJSON user;
    
    @JsonCreator
    public SearchUserJSON(UserJSON user) {
        super(Searchable.USER);
        this.user = user;
    }

    public UserJSON getUser() {
        return user;
    }

    public void setUser(UserJSON user){
        this.user = user;
    }
}
