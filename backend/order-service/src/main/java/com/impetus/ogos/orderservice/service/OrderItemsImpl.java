package com.impetus.ogos.orderservice.service;

import com.impetus.ogos.orderservice.dto.OrderItemsDto;
import com.impetus.ogos.orderservice.model.OrderItems;
import com.impetus.ogos.orderservice.repository.OrderDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemsImpl implements OrderItemsService {
    static Logger logger = LoggerFactory.getLogger(OrderItemsService.class);
    @Autowired
    private OrderDetailsRepository orderDetailsRepo;


    /**
     * This  method is  taking order items from OrderItemsDto  and setting the value in it.
     * @param orderItemDto
     * @return  orderItem
     */



    public  OrderItems getOrderItemFromOrderItemDto(OrderItemsDto orderItemDto)
    {
        logger.info("inside getOrderItemFromOrderItemDto method of OrderItemsService");
        OrderItems orderItem = new OrderItems();
orderItem.setProductName(orderItemDto.getProductName());
        orderItem.setProductId(orderItemDto.getProductId());
        orderItem.setQuantity(orderItemDto.getQuantity());

        orderItem.setPurchasePrice(orderItemDto.getPurchasePrice());
        return  orderItem;


    }
}
