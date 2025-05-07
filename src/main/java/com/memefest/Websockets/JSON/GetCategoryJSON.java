package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.CategoryJSON;

@JsonRootName("GetCategory")
public class GetCategoryJSON {

    @JsonProperty("Categories")
    private Set<CategoryJSON> categories;
    
    @JsonCreator
    public GetCategoryJSON(@JsonProperty("Categories") Set<CategoryJSON> categories) {
        this.categories = categories;
    }

    @JsonProperty("Categories")
    public Set<CategoryJSON> getCategories() {
        return this.categories;
    }

    public void setCategories(Set<CategoryJSON> categories) {
        this.categories = categories;
    }
}
