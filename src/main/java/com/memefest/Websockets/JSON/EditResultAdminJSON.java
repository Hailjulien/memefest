package com.memefest.Websockets.JSON;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.AdminJSON;
import com.memefest.DataAccess.JSON.UserJSON;

@JsonRootName("EditResultsAdmin")
public class EditResultAdminJSON extends EditResultUserJSON{
    
    @JsonProperty("Admins")
    private Set<AdminJSON> admins;

    @JsonCreator
    public EditResultAdminJSON(@JsonProperty("ResultCode") int resultCode, @JsonProperty("ResultMessage") String resultMessage,
                                    @JsonProperty("Admins") Set<AdminJSON> admins){
        super(resultCode, resultMessage, admins.stream().map(candidate -> {
            return (UserJSON) candidate;
        }).collect(Collectors.toSet()));
        this.admins = admins;
    }

    public Set<AdminJSON> getAdmins(){
        return this.admins;
    }

    public void setAdmins(Set<AdminJSON> admins){
        this.admins = admins;
        super.setUsers(admins.stream().map(candidate -> {
            return (UserJSON) candidate;
        }).collect(Collectors.toSet()));
    }
}
