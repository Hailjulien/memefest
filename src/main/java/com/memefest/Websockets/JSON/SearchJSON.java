package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "SearchId")
public abstract class SearchJSON {
    
    @JsonProperty("SearchType")
    private Searchable searchable;

    @JsonProperty("SearchId")
    private int searchId;

    @JsonCreator
    public SearchJSON(@JsonProperty("SearchType") Searchable search){
        this.searchable = search;
    }

    public Searchable getSearchable() {
        return searchable;
    }

    public int getSearchId(){
        return searchId;
    }

    public void setSearchId(int searchId){
        this.searchId = searchId;
    }
}
