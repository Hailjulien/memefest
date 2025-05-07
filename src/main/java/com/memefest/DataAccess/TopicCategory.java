package com.memefest.DataAccess;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
  @NamedQuery(
    name = "TopicCategory.getByTopicId", 
    query = "SELECT tc FROM TopicCategoryEntity tc WHERE tc.topicId = :topicId"),
  @NamedQuery(
    name = "TopicCategory.getByCategoryId",
    query = "SELECT tc FROM TopicCategoryEntity tc WHERE tc.categoryId = :categoryId")
})
@Entity(name = "TopicCategoryEntity")
@Table(name = "TOPIC_CATEGORY")
public class TopicCategory  extends Topic{
  
  @Column(name = "Cat_Id", nullable = false, updatable = false, insertable = false)
  private int categoryId; 

  @ManyToMany(cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "Cat_Id", referencedColumnName = "Cat_Id")
  private Set<Category> categories;
  
  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }
  
  public Set<Category> getCategories() {
    return this.categories;
  }
  
  public int getCat_Id(){
    return categoryId;
  }

  public void setCat_Id(int catId){
    this.categoryId = catId;
  }
}
