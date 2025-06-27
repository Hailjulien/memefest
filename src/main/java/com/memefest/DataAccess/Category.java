package com.memefest.DataAccess;

import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityResult;
import jakarta.persistence.FetchType;
import jakarta.persistence.FieldResult;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;

@NamedNativeQueries({
    @NamedNativeQuery(name = "Category.getCategoryByTitle",
    query = "SELECT TOP(1) C.Cat_Id as categoryId, C.Cat_Name as categoryName FROM CATEGORY C "
                + "WHERE C.Cat_Name LIKE CONCAT('%',CONCAT(?,'%'))", resultSetMapping = "CategoryEntityMapping"
    ),

    @NamedNativeQuery(name = "Category.searchByTitle",
    query = "SELECT C.Category_Id as categoryId, C.Cat_Name as categoryName FROM CATEGORY C "
                + "WHERE C.Cat_Name LIKE CONCAT('%', CONCAT(?,'%'))", resultSetMapping = "CategoryEntityMapping"
    )
})
@SqlResultSetMappings(
    @SqlResultSetMapping(
        name = "CategoryEntityMapping",
        entities = {
            @EntityResult(
                entityClass = Category.class,
                fields = {
                    @FieldResult(name = "categoryId", column = "categoryId"),
                    @FieldResult(name = "categoryName", column = "categoryName")
                }
            )
        }
    )   
)
@Table(name = "CATEGORY")
@Entity(name = "CategoryEntity")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cat_Id")
    private int categoryId;

    @Column(name = "Cat_Name")
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
    @JoinColumn(name = "Cat_Id", referencedColumnName = "Cat_Id")
    private Set<SubCategory> subcategories;
  
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    @JoinColumn(name = "Cat_Id", referencedColumnName = "Cat_Id")
    private Set<TopicCategory> topics;

    public void setTopics(Set<TopicCategory> topics){
        this.topics = topics;
    }

    public Set<TopicCategory> getTopics(){
        return topics;
    }
    
    public void setCat_Id(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCat_Id() {
        return this.categoryId;
    }

    public void setCat_Name(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCat_Name() {
        return this.categoryName;
    }

    public void setSubcategories(Set<SubCategory> subcategories) {
        this.subcategories = subcategories;
    }

    public Set<SubCategory> getSubcategories() {
        return this.subcategories;
    }
}
