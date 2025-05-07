package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.CategoryJSON;

@JsonRootName("GetResultCategory")
public class GetResultCategoryJSON extends ResultJSON{

    @JsonProperty("Categories")
    private Set<CategoryJSON> categories;

    @JsonCreator
    public GetResultCategoryJSON(@JsonProperty("ResultCode") int resultCode, @JsonProperty("ResultMessage") String resultMessage,
                                    @JsonProperty("Categories") Set<CategoryJSON> categories){
        super(resultCode, resultMessage);
        this.categories = categories;
    }

    public Set<CategoryJSON> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryJSON> categories) {
        this.categories = categories;
    }
}