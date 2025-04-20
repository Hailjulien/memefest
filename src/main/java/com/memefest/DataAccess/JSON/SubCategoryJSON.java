package com.memefest.DataAccess.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.Set;

@JsonRootName("SubCategory")
public class SubCategoryJSON extends CategoryJSON {
  public Set<CategoryJSON> parentCategories;
  
  public SubCategoryJSON(@JsonProperty("CatId") int categoryId, @JsonProperty("CatName") String categoryName, @JsonProperty("Topics") Set<TopicJSON> topics, Set<CategoryJSON> parentCategories) {
    super(categoryId, categoryName, topics, null, null);
    this.parentCategories = parentCategories;
  }
  
  public Set<CategoryJSON> getParentCategories() {
    return this.parentCategories;
  }
  
  public void setParentCategories(Set<CategoryJSON> parentCategories) {
    this.parentCategories = parentCategories;
  }
}
