package com.vaibhav.ecommerce.Service;

import com.vaibhav.ecommerce.Model.User;
import com.vaibhav.ecommerce.Model.Wishlist;
import com.vaibhav.ecommerce.Repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    public void createWishlist(Wishlist wishlist){
        wishlistRepository.save(wishlist);
    }

    public List<Wishlist> readWishlist(User user){
        return wishlistRepository.findAllByUserOrderByCreatedDateDesc(user);
    }
}
