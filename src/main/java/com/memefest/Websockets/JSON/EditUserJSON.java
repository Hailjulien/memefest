package com.memefest.Websockets.JSON;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.memefest.DataAccess.JSON.UserJSON;

public class EditUserJSON extends EditJSON {
    @JsonProperty("Users")
    private Set<UserJSON> users;

    @JsonCreator
    public EditUserJSON(@JsonProperty("Users") Set<UserJSON> users){
        super(Editable.USER);
        this.users = users;
        int editId = users.stream().map(candidate ->{
            return candidate.hashCode();
        }).reduce(40, (x, y)-> x.hashCode() + y.hashCode());
        super.setEditId(editId);
    }

    
    public Set<UserJSON> getUsers(){
        return this.users;
    }

    public void setUsers(Set<UserJSON> users){
        this.users = users;
    }
}