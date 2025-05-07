package com.memefest.Websockets.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.CategoryJSON;

@JsonRootName("SearchCategory")
public class SearchCategoryJSON extends SearchJSON{
    
    @JsonProperty("Category")
    private  CategoryJSON category;

    public SearchCategoryJSON(@JsonProperty("Category") CategoryJSON category) {
        super(Searchable.CATEGORY);
        this.category = category;
    }

    public CategoryJSON getCategory() {
        return category;
    }

    public void setCategory(CategoryJSON category) {
        this.category = category;
    }

}
