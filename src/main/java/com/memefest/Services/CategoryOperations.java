package com.memefest.Services;

import java.util.Set;

import com.memefest.DataAccess.Category;
import com.memefest.DataAccess.CategoryFollower;
import com.memefest.DataAccess.MainCategory;
import com.memefest.DataAccess.SubCategory;
import com.memefest.DataAccess.JSON.CategoryJSON;
import com.memefest.DataAccess.JSON.SubCategoryJSON;
import com.memefest.DataAccess.JSON.TopicJSON;

public interface CategoryOperations {
 
    public CategoryJSON getCategoryInfo(CategoryJSON category);
    
    public Set<TopicJSON> getCategoryTopics(CategoryJSON category);

    public void createCategory(CategoryJSON category);

    public void editCategory(CategoryJSON category);

    public void createCategoryFollowers(CategoryJSON category);
    
    public void removeCategoryFollowers(CategoryJSON categoory);

    public void createSubCategory(SubCategoryJSON subCategory);

    public void editSubCategory(CategoryJSON subCategory);

    public void createMainCategory(CategoryJSON mainCategory);

    public void editMainCategory(CategoryJSON mainCategory);

    public void removeCategory(CategoryJSON category);

    public Category getCategoryEntity(CategoryJSON category);

    public MainCategory getMainCategoryEntity(CategoryJSON category);

    public SubCategory getSubCategoryEntity(CategoryJSON category);

    public CategoryJSON getMainCategoryInfo(CategoryJSON category);

    public Set<CategoryJSON> getSubCategoryInfo(CategoryJSON category);

    public MainCategory getMainCategoryFromCategory(CategoryJSON category);

    public Set<CategoryJSON> searchCategory(CategoryJSON category);

    public Set<CategoryFollower> getCategoryFollowers(CategoryJSON category);



}
