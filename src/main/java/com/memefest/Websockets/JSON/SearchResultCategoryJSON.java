package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.CategoryJSON;

@JsonRootName("SearchResultsForCategory")
public class SearchResultCategoryJSON extends SearchResultJSON{
    
    @JsonProperty("Categories")
    private Set<CategoryJSON> categories;

    @JsonCreator
    public SearchResultCategoryJSON(@JsonProperty("Categories") Set<CategoryJSON> categories,
                                        @JsonProperty("ResultCode") int resultCode,
                                            @JsonProperty("ResultMessage") String resultMessage) {
        super(Searchable.CATEGORY, resultCode, resultMessage);

        this.categories = categories;
    }

    public Set<CategoryJSON> getCategories(){
        return this.categories;
    }

    public void setCategories(Set<CategoryJSON> categories) {
        this.categories = categories;
    }
}
