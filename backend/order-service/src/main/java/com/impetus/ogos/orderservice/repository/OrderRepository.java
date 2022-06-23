package com.impetus.ogos.orderservice.repository;


import com.impetus.ogos.orderservice.dto.OrderDto;
import com.impetus.ogos.orderservice.dto.OrderItemsDto;
import com.impetus.ogos.orderservice.model.Order;



import com.impetus.ogos.orderservice.model.enumeration.OrderStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllOrderByStage(OrderStage status);

List<Order> findAllOrderByUserId(String userId);



    
}
