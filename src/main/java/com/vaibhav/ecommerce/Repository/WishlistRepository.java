package com.vaibhav.ecommerce.Repository;

import com.vaibhav.ecommerce.Model.User;
import com.vaibhav.ecommerce.Model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist,Integer> {
    List<Wishlist> findAllByUserOrderByCreatedDateDesc(User user);
}
