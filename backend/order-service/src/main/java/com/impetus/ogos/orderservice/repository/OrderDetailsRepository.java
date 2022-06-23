package com.impetus.ogos.orderservice.repository;

import com.impetus.ogos.orderservice.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderItems,Integer> {
}
