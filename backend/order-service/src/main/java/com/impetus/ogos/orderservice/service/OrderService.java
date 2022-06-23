package com.impetus.ogos.orderservice.service;

import com.impetus.ogos.orderservice.dto.FeedbackDto;
//import com.impetus.ogos.orderservice.model.Order;age
import com.impetus.ogos.orderservice.dto.OrderDto;
import com.impetus.ogos.orderservice.dto.PlaceOrderDto;
import com.impetus.ogos.orderservice.model.Order;

import com.impetus.ogos.orderservice.model.enumeration.OrderStage;

import java.util.List;

public interface OrderService {

    public void createOrder(PlaceOrderDto orderDto);
    public   Order getOrderFromDto(PlaceOrderDto orderDto);
    public List<Order> getAllOrders();
    public Order getOrder(int orderId);
    public void changeOrderStatus(int id);
    public List<Order> findOrderByStatus(OrderStage status);
//    public  List<Order> fetchOrderByUserId(String userId);
    public void addFeedback(FeedbackDto feedbackDto);
    public List<OrderDto> listOrders() ;
    public List<OrderDto> fetchOrderByUserId(String userId);






    }
