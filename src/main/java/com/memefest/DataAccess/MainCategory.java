package com.memefest.DataAccess;

import com.memefest.DataAccess.MainCategory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FieldResult;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;
import java.util.Set;

@NamedNativeQueries({
  @NamedNativeQuery(name = "MainCategory.getCategoryByTitle",
    query = "SELECT TOP(1) M.Cat_Id as categoryId, C.Cat_Name as catName FROM MAINCATEGORY M JOIN CATEGORY C ON "
      + "M.Cat_Id = C.Cat_Id WHERE C.Cat_Name LIKE CONCAT('%', :title, '%')", 
    resultSetMapping = "MainCategoryEntityMapping")})
@SqlResultSetMappings({
  @SqlResultSetMapping(
    name = "MainCategoryEntityMapping",
    entities = {
      @EntityResult(
        entityClass = MainCategory.class, 
        fields = {
          @FieldResult(name = "categoryId", column = "Cat_Id"), 
          @FieldResult(name = "categoryName", column = "Cat_Name")}
        )
      }
  )
})
@Entity(name = "MainCategoryEntity")
@Table(name = "MAINCATEGORY")
public class MainCategory extends Category {
  
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "mainCategory")
  @JoinColumn(name = "Cat_Id", referencedColumnName = "Parent_Id")
  private Set<Topic> topics;

  @OneToMany(mappedBy = "parentCategory")
  public Set<SubCategory> getSubcategories() {
    return super.getSubcategories();
  }
  
  @OneToMany(mappedBy = "parentCategory")
  public void setSubcategories(Set<SubCategory> subcategories) {
    super.setSubcategories(subcategories);
  }

  public void setDirectTopics(Set<Topic> topics){
    this.topics = topics;
    topics.addAll(getTopics());
    super.setTopics(getTopics());
  }

  public Set<Topic> getDirectTopics(){
    return topics;
  }
}
