package com.impetus.ogos.orderservice.service;
import com.impetus.ogos.orderservice.dto.OrderItemsDto;
import com.impetus.ogos.orderservice.model.OrderItems;

public interface OrderItemsService {

    public OrderItems getOrderItemFromOrderItemDto(OrderItemsDto orderItemDto);
}
