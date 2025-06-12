package com.memefest.Websockets.JSON;

import java.util.Set;
import com.memefest.DataAccess.JSON.CategoryJSON;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("EditCategory")
public class EditCategoryJSON extends EditJSON{
    
    @JsonProperty("Categories")
    private Set<CategoryJSON> categories; 
    
    @JsonCreator
    public EditCategoryJSON(@JsonProperty("Categories") Set<CategoryJSON> categories) {
        super(Editable.CATEGORY);
        this.categories = categories;
    }

    public Set<CategoryJSON> getCategories(){
        return this.categories;
    }

    public void setCategories(Set<CategoryJSON> categories){
        this.categories = categories;
    }
}