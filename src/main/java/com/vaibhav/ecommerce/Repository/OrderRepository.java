package com.vaibhav.ecommerce.Repository;

import com.vaibhav.ecommerce.Model.Order;
import com.vaibhav.ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByUserOrderByCreatedDateDesc(User user);
}
