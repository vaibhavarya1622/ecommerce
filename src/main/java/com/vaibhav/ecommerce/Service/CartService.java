package com.vaibhav.ecommerce.Service;

import com.vaibhav.ecommerce.Model.Cart;
import com.vaibhav.ecommerce.Model.Product;
import com.vaibhav.ecommerce.Model.User;
import com.vaibhav.ecommerce.Repository.CartRepository;
import com.vaibhav.ecommerce.dto.AddToCartDto;
import com.vaibhav.ecommerce.dto.CartDto;
import com.vaibhav.ecommerce.dto.CartItemDto;
import com.vaibhav.ecommerce.exceptions.CartItemNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    public void addToCart(AddToCartDto addToCartDto, Product product, User user){
        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
        cartRepository.save(cart);
    }
    public CartDto listCartDtoItems(User user){
        //first get all the cart items for the users
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        for(Cart cartItem:cartList){
            CartItemDto cartItemDto = new CartItemDto(cartItem);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for(CartItemDto cartItemDto:cartItems){
            totalCost += cartItemDto.getProduct().getPrice()*cartItemDto.getQuantity();
        }
        return new CartDto(cartItems,totalCost);
    }
    public void deleteCartItem(Integer cartItemId, User user) throws CartItemNotExistException{
        Optional<Cart> optionalCart=cartRepository.findById(cartItemId);
        if(!optionalCart.isPresent()){
            throw new CartItemNotExistException("Item is not present in Cart");
        }
        Cart cart = optionalCart.get();
        if(cart.getUser() != user){
            throw new CartItemNotExistException("cart item does not belong to the user");
        }
        cartRepository.deleteById(cartItemId);
    }
}
