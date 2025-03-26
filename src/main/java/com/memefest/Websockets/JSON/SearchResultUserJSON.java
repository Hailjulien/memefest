package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.UserJSON;

@JsonRootName("SearchResultUser")
public class SearchResultUserJSON extends SearchResultJSON{
    
    @JsonProperty("Users")
    private Set<UserJSON> users;

    @JsonCreator
    public SearchResultUserJSON(@JsonProperty("Users") Set<UserJSON> users, 
                                    @JsonProperty("ResultCode") int resultCode,
                                        @JsonProperty("ResultMessage") String resultMessage) {
        super(Searchable.USER,resultCode,resultMessage);
        this.users = users;
    }

    public Set<UserJSON> getUsers() {
        return users;
    }

    public void setUsers(Set<UserJSON> users) {
        this.users = users;
    }
}
