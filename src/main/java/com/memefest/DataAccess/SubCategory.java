package com.memefest.DataAccess;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import java.util.Set;

@NamedNativeQueries({
  @NamedNativeQuery(
    name = "SubCategory.getCategoryByTitle",
    query = "SELECT TOP(1) S.Cat_Id as categoryId,C.Cat_Name as categoryName,S.Parent_Id as parentId FROM SUBCATEGORY S " 
    + "JOIN CATEGORY C ON S.Cat_Id = C.Cat_Id WHERE C.Cat_Name LIKE CONCAT('%', :title, '%')",
    resultSetMapping = "SubCategoryEntityMapping")
})
@SqlResultSetMappings({
  @SqlResultSetMapping(
    name = "SubCategoryEntityMapping",
    entities = {
      @EntityResult(
        entityClass = MainCategory.class, 
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
public class SubCategory extends Category {
  @Column(name = "Parent_Id", updatable = false, nullable = false, insertable = false)
  private int parentId;

  @Column(name = "Topic_Id", updatable = true, nullable = true, insertable = true)
  private int topicId;
  
  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "Parent_Id", referencedColumnName = "Cat_Id")
  private MainCategory parentCategory;
  
  @ManyToMany(cascade = {CascadeType.ALL})
  private Set<SubCategory> parentCategories;

  @ManyToMany(cascade = CascadeType.ALL, mappedBy = "subCategories")
  @JoinColumn(name = "Topic_Id", referencedColumnName = "Topic_Id")
  private Set<Topic> topics;
  
  public int getParent_Id() {
    return this.parentId;
  }
  
  public void setParent_Id(int parentId) {
    this.parentId = parentId;
  }
  
  public Set<SubCategory> getParentCategories() {
    return this.parentCategories;
  }
  
  public void setParentCategories(Set<SubCategory> parentCategories) {
    this.parentCategories = parentCategories;
  }
  
  public Category getMainCategory() {
    return (Category)this.parentCategory;
  }
  
  public void setMainCategory(MainCategory parentCategory) {
    this.parentCategory = parentCategory;
  }

  public Set<Topic> getDirectTopics(){
    return this.topics;
  }

  public void setDirectTopics(Set<Topic> topics){
    this.topics = topics;
  }
}
