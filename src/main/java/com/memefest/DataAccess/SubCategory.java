package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FetchType;
import jakarta.persistence.FieldResult;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;

@NamedNativeQueries({
  @NamedNativeQuery(
    name = "SubCategory.getCategoryByTitle",
    query = "SELECT TOP(1) S.Cat_Id as categoryId,C.Cat_Name as categoryName,S.Parent_Id as parentId FROM SUBCATEGORY S " 
    + "RIGHT OUTER JOIN CATEGORY C ON S.Cat_Id = C.Cat_Id WHERE C.Cat_Name LIKE CONCAT('%'CONCAT(?, '%'))",
    resultSetMapping = "SubCategoryEntityMapping")
})
@SqlResultSetMappings({
  @SqlResultSetMapping(
    name = "SubCategoryEntityMapping",
    entities = {
      @EntityResult(
        entityClass = SubCategory.class, 
        fields = {
          @FieldResult(name = "categoryId", column = "Cat_Id"),
          @FieldResult(name = "categoryName", column = "Cat_Name"), 
          @FieldResult(name = "parentId", column = "Parent_Id")}
      )
    }
  )
})
@Entity(name = "SubCategoryEntity")
@Table(name = "SUBCATEGORY")
public class SubCategory{

  @EmbeddedId
  private SubCategoryId subCategoryId;

  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Cat_Id", referencedColumnName = "Cat_Id")
  private Category category;
  
  @ManyToOne(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Parent_Id", referencedColumnName = "Cat_Id")
  private Category parent; 

/* 
  @ManyToOne(cascade =  {CascadeType.PERSIST})
  @JoinColumn(name = "Parent_Id", referencedColumnName = "Cat_Id")
  private MainCategory mainCategory;
*/
  public void setCategory(Category category) {
    this.subCategoryId.setCat_Id(category.getCat_Id());
    this.category = category;
  }
  
  public Category getCategory() {
    return this.category;
  }

  public void setParent(Category parent){
    this.subCategoryId.setParent_Id(parent.getCat_Id());
    this.parent = parent;
  }

  public Category getParent(){
    return this.parent;
  }
  
  public int getCat_Id(){
    return subCategoryId.getCat_Id();
  }

  public void setCat_Id(int catId){
    this.subCategoryId.setCat_Id(catId);
  }

  public int getTopic_Id(){
    return subCategoryId.getCat_Id();
  }

  public void setTopic_Id(int categoryId){
    this.subCategoryId.setCat_Id(categoryId);
  }
/*
  public void setMainCategory(MainCategory mainCategory){
    this.mainCategory = mainCategory;
  }

  public MainCategory getMainCategory(){
    return this.mainCategory;
  }
  */
}
