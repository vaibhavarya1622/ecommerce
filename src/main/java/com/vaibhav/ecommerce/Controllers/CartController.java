package com.vaibhav.ecommerce.Controllers;

import com.vaibhav.ecommerce.Model.Product;
import com.vaibhav.ecommerce.Model.User;
import com.vaibhav.ecommerce.Service.AuthenticationService;
import com.vaibhav.ecommerce.Service.CartService;
import com.vaibhav.ecommerce.Service.ProductService;
import com.vaibhav.ecommerce.config.ApiResponse;
import com.vaibhav.ecommerce.dto.AddToCartDto;
import com.vaibhav.ecommerce.dto.CartDto;
import com.vaibhav.ecommerce.exceptions.AuthenticationFailException;
import com.vaibhav.ecommerce.exceptions.CartItemNotExistException;
import com.vaibhav.ecommerce.exceptions.ProductNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestParam("token") String token)
    throws AuthenticationFailException, ProductNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        Product product = productService.getProductById(addToCartDto.getProductId());
        cartService.addToCart(addToCartDto,product,user);
        return new ResponseEntity<>(new ApiResponse(true,"Added to cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException{
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        CartDto cartDto =   cartService.listCartDtoItems(user);
        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
    }

    //task delete cart item
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") int cartItemId, @RequestParam("token") String token)
    throws AuthenticationFailException, CartItemNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        //method to be completed
        cartService.deleteCartItem(cartItemId, user);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"Item has been removed"),HttpStatus.OK);
    }
}
