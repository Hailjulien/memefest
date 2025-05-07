package com.memefest.Websockets.JSON;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.memefest.DataAccess.JSON.AdminJSON;

public class EditAdminJSON extends EditJSON{
    @JsonProperty("Admins")
    private Set<AdminJSON> admins;

    @JsonCreator
    public EditAdminJSON(@JsonProperty("Admins") Set<AdminJSON> admins){
        super(Editable.USER);
        this.admins = admins;
        this.setEditId( admins.stream().map(candidate ->{
                return candidate.hashCode();
        }).reduce(0, (x, y)-> x.hashCode() + y.hashCode()));
    }

    public Set<AdminJSON> getAdmins(){
        return this.admins;
    }

    public void setAdmins(Set<AdminJSON> admins){
        this.admins = admins;
    }
}
