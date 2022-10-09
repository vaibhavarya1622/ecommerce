package com.vaibhav.ecommerce.Service;

import com.vaibhav.ecommerce.Model.Category;
import com.vaibhav.ecommerce.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public Category readCategory(String categoryName){
        return categoryRepository.findByCategoryName(categoryName);
    }
    public Optional<Category> readCategory(Integer categoryID){
        return categoryRepository.findById(categoryID);
    }
    public void createCategory(Category category){
        categoryRepository.save(category);
    }
    public List<Category> listCategories(){
        return categoryRepository.findAll();
    }

    public void updateCategory(Integer categoryID, Category newCategory){
        Category category = categoryRepository.findById(categoryID).get();
        category.setCategoryName(newCategory.getCategoryName());
        category.setDescription(newCategory.getDescription());
        category.setImageUrl(newCategory.getImageUrl());
        categoryRepository.save(category);
    }
}
