package com.vaibhav.ecommerce.Repository;

import com.vaibhav.ecommerce.Model.Cart;
import com.vaibhav.ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
    Optional<Cart> findById(Integer id);

    @Override
    List<Cart> findAll();
}
