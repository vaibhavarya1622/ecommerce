package com.vaibhav.ecommerce.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "category_name")
    private @NotBlank String categoryName;
    private @NotBlank String description;
    @Column(length = 65555)
    private @NotBlank String imageUrl;

    public Category(){}

    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public Category(String categoryName, String description, String imageUrl) {
        this.categoryName = categoryName;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return Id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @Override
    public String toString(){
        return "User {category id=" + Id + ", category name='" + categoryName + "', description='" + description + "'}";
    }
}
