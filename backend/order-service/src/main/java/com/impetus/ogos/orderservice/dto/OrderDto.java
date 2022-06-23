package com.impetus.ogos.orderservice.dto;

import com.impetus.ogos.orderservice.model.Order;
import com.impetus.ogos.orderservice.model.OrderItems;
import com.impetus.ogos.orderservice.model.enumeration.OrderStage;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    private int orderId;

    private LocalDateTime timeOfOrder;
    private LocalDateTime stageDate;
    private String userId;
    private int addressId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private double totalPrice;
    private OrderStage stage;
    private List<OrderItems> orderItems;

    public LocalDateTime getStageDate() {
        return stageDate;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getTimeOfOrder() {
        return timeOfOrder;
    }

    public void setTimeOfOrder(LocalDateTime timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    public LocalDateTime getStageDate(LocalDateTime stageDate) {
        return this.stageDate;
    }

    public void setStageDate(LocalDateTime stageDate) {
        this.stageDate = stageDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStage getStage() {
        return stage;
    }

    public void setStage(OrderStage stage) {
        this.stage = stage;
    }

    public List<OrderItemsDto> getOrderItemsDtoList() {
        return orderItemsDtoList;
    }

    public void setOrderItemsDtoList(List<OrderItemsDto> orderItemsDtoList) {
        this.orderItemsDtoList = orderItemsDtoList;
    }

    private List<OrderItemsDto> orderItemsDtoList;


    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public OrderDto(Order order)
    {
        this.setOrderId(order.getOrderId());
        this.setUserId(order.getUserId());
        this.setTimeOfOrder(order.getTimeOfOrder());
        this.setStage(order.getStage());
        this.setTotalPrice(order.getTotalPrice());
        this.setStageDate(order.getStageDate());
        this.setAddressId(order.getAddressId());
this.setOrderItems(order.getOrderDetails());
setOrderItems(orderItems);


   }




    public OrderDto(int orderId, LocalDateTime timeOfOrder, LocalDateTime stageDate, double totalPrice, OrderStage stage, List<OrderItemsDto> orderItemsDtoList,int userID,int addressId) {
        this.orderId = orderId;
        this.timeOfOrder = timeOfOrder;
        this.stageDate = stageDate;
        this.totalPrice = totalPrice;
        this.stage = stage;
        this.addressId=addressId;
        this.orderItemsDtoList = orderItemsDtoList;
    }

    public OrderDto() {

    }
}
