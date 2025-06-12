package com.memefest.DataAccess.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.Set;

@JsonRootName("SubCategory")
public class SubCategoryJSON extends CategoryJSON {
  
  @JsonProperty("ParentCategories")
  private Set<CategoryJSON> parentCategories;

  @JsonProperty("CancelParentCategories")
  private Set<CategoryJSON> canceledCats;  

  //@JsonProperty("MainCategory")
  //private CategoryJSON mainCategory;
  
  public SubCategoryJSON(@JsonProperty("CatId") int categoryId, @JsonProperty("CatName") String categoryName,
                          //@JsonProperty("MainCategory") CategoryJSON mainCategory,
                           @JsonProperty("Topics") Set<TopicJSON> topics, 
                            @JsonProperty("ParentCategories") Set<CategoryJSON> parentCategories,
                              @JsonProperty("CancelParentCategories") Set<CategoryJSON> canceledCats ) {
    super(categoryId, categoryName, topics, null, null);
    this.parentCategories = parentCategories;
    //this.mainCategory = mainCategory;
    this.canceledCats = canceledCats;
  }
  
  public Set<CategoryJSON> getParentCategories() {
    return this.parentCategories;
  }
  
  public void setParentCategories(Set<CategoryJSON> parentCategories) {
    this.parentCategories = parentCategories;
  }

  public Set<CategoryJSON> getCanceledParentCategories() {
    return this.canceledCats;
  }
  
  public void setCanceledParentCategories(Set<CategoryJSON> canceledParentCategories) {
    this.canceledCats = canceledParentCategories;
  }

  /* 
  public void setMainCategory(CategoryJSON category){
    this.mainCategory = category;
  }

  public CategoryJSON getMainCategoryJSON(){
    return this.mainCategory;
  }
  */
}
