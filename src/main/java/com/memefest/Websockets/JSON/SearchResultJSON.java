package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("SearchResult")
public abstract class SearchResultJSON extends ResultJSON{
    
    @JsonProperty("Searchable")
    private Searchable searchable;

    @JsonCreator
    public SearchResultJSON(@JsonProperty("Searchable") Searchable searchable,
                                @JsonProperty("ResultCode") int resultCode,
                                    @JsonProperty("ResultMessage") String resultMessage) {
        super(resultCode, resultMessage);
        this.searchable = searchable;
    }
    
    public Searchable getSearchable() {
        return searchable;
    }

    public void setSearchable(Searchable searchable) {
        this.searchable = searchable;
    }

}
