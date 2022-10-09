package com.vaibhav.ecommerce.Controllers;

import com.vaibhav.ecommerce.Model.Product;
import com.vaibhav.ecommerce.Model.User;
import com.vaibhav.ecommerce.Model.Wishlist;
import com.vaibhav.ecommerce.Repository.ProductRepository;
import com.vaibhav.ecommerce.Service.AuthenticationService;
import com.vaibhav.ecommerce.Service.WishlistService;
import com.vaibhav.ecommerce.config.ApiResponse;
import com.vaibhav.ecommerce.dto.ProductDto;
import com.vaibhav.ecommerce.exceptions.AuthenticationFailException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
    @Autowired
    WishlistService wishlistService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addWishlist(@RequestBody ProductDto productDto, @RequestParam("token")String token) throws AuthenticationFailException {
        //first authenticate whether the token is valid
        authenticationService.authenticate(token);
        //then fetch the user linked to the token
        User user = authenticationService.getUser(token);

        //get the product from product repo
        Product product = productRepository.getById(productDto.getId());
        Wishlist wishlist = new Wishlist(user,product);
        wishlistService.createWishlist(wishlist);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Added to wishlist"), HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishlist(@PathVariable("token") String token) throws AuthenticationFailException{
        //first authenticate if the token is valid
        authenticationService.authenticate(token);
        //then fetch the user linked to the token
        User user = authenticationService.getUser(token);

        //first retrieve the wishlist items
        List<Wishlist> wishlists = wishlistService.readWishlist(user);
        List<ProductDto> products = new ArrayList<>();

        for(Wishlist wishlist:wishlists){
            products.add(new ProductDto(wishlist.getProduct()));
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
 }
