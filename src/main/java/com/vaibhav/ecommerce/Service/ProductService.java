package com.vaibhav.ecommerce.Service;

import com.vaibhav.ecommerce.Model.Category;
import com.vaibhav.ecommerce.Model.Product;
import com.vaibhav.ecommerce.Repository.ProductRepository;
import com.vaibhav.ecommerce.dto.ProductDto;
import com.vaibhav.ecommerce.exceptions.ProductNotExistException;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDto> listProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product:products){
            productDtos.add(new ProductDto(product));
        }
        return productDtos;
    }
    public Product getProductById(Integer productId) throws ProductNotExistException{
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(!optionalProduct.isPresent()){
            throw new ProductNotExistException("Product id is invalid" + productId);
        }
        return optionalProduct.get();
    }
    public void addProduct(ProductDto productDto, Category category){
        Product product = getProductFromDto(productDto, category);
        productRepository.save(product);
    }
    public void updateProduct(Integer productID, ProductDto productDto, Category category){
        Product product = getProductFromDto(productDto,category);
        product.setId(productID);
        productRepository.save(product);
    }
    public static Product getProductFromDto(ProductDto productDto, Category category){
        Product product = new Product();
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageUrl());
        product.setPrice(productDto.getPrice());
        product.setName(productDto.getName());
        return product;
    }
}
