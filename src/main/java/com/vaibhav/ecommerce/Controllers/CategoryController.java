package com.vaibhav.ecommerce.Controllers;

import com.vaibhav.ecommerce.Model.Category;
import com.vaibhav.ecommerce.Service.CategoryService;
import com.vaibhav.ecommerce.config.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category){
        if(Objects.nonNull(categoryService.readCategory(category.getCategoryName()))){
            return new ResponseEntity<>(new ApiResponse(false,"category already exits"),HttpStatus.CONFLICT);
        }
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true,"created the category"),HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> body = categoryService.listCategories();
        return new ResponseEntity<>(body,HttpStatus.OK);
    }
    @PostMapping("/update/{categoryID}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryID") Integer categoryID, @Valid @RequestBody Category category){
        if(Objects.nonNull(categoryService.readCategory(categoryID))){
             categoryService.updateCategory(categoryID, category);
             return new ResponseEntity<>(new ApiResponse(true,"Updated the category"),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(true, "category does not exist"),HttpStatus.NOT_FOUND);
    }
}
