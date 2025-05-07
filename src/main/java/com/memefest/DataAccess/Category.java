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
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.SqlResultSetMappings;
import jakarta.persistence.Table;


@NamedNativeQueries({
    @NamedNativeQuery(name = "Category.getTopicByTitle",
    query = "SELECT TOP(1) C.Category_Id as categoryId, C.Cat_Name as categoryName FROM TOPIC C "
                + "WHERE C.title LIKE CONCAT('%', :title, '%')", resultSetMapping = "CategoryEntityMapping"
    ),
    @NamedNativeQuery(name = "Category.searchByName",
    query = "SELECT C.Category_Id as categoryId, C.Cat_Name as categoryName FROM TOPIC C "
                + "WHERE C.title LIKE CONCAT('%', :title, '%')", resultSetMapping = "CategoryEntityMapping"
    )
})
@SqlResultSetMappings(
    @SqlResultSetMapping(
        name = "CategoryEntityMapping",
        entities = {
            @EntityResult(
                entityClass = MainCategory.class,
                fields = {
                    @FieldResult(name = "categoryId", column = "Cat_Id"),
                    @FieldResult(name = "categoryName", column = "Cat_Name"),
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    //@JoinColumn(name = "Topic_Id", referencedColumnName = "Topic_Id")
    private Set<TopicCategory> topics;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "parentCategories")
    @JoinColumn(name = "Cat_Id", referencedColumnName = "Parent_Id")
    private Set<SubCategory> subcategories;

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

    public void setTopics(Set<TopicCategory> topics) {
        this.topics = topics;
    }

    public Set<TopicCategory> getTopics() {
        return this.topics;
    }

    public void setSubcategories(Set<SubCategory> subcategories) {
        this.subcategories = subcategories;
    }

    public Set<SubCategory> getSubcategories() {
        return this.subcategories;
    }
}
