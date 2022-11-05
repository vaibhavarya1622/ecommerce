package com.vaibhav.ecommerce.Repository;

import com.vaibhav.ecommerce.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
