package com.memefest.Websockets.JSON;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.memefest.DataAccess.JSON.CategoryJSON;

@JsonRootName("EditResultsForCategory")
public class EditResultCategoryJSON extends EditResultJSON{
    
    @JsonProperty("Categories")
    private Set<CategoryJSON> categories;

    @JsonCreator
    public EditResultCategoryJSON(@JsonProperty("Categories") Set<CategoryJSON> categories,
                                    @JsonProperty("ResultCode") int resultCode,
                                        @JsonProperty("ResultMessage") String resultMessage) {
        super(Editable.CATEGORY, resultCode, resultMessage); 
        this.categories = categories;
    }
    
    public Set<CategoryJSON> getCategories(){
        return this.categories;
    }

    public void setCategories(Set<CategoryJSON> categories){
        this.categories = categories;
    }
}
