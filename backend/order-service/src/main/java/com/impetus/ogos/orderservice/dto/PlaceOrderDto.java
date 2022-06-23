package com.impetus.ogos.orderservice.dto;

import com.impetus.ogos.orderservice.model.Order;

import javax.validation.constraints.NotNull;
import java.util.List;

public class PlaceOrderDto {
    private @NotNull String userId;
    private @NotNull double totalPrice;
    private  @NotNull int addressId;
private  @NotNull String paymentId;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    private List<OrderItemsDto> orderItemsDtoList;

    public PlaceOrderDto() {

    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public PlaceOrderDto(String userId, double totalPrice, int addressId, String paymentId,List<OrderItemsDto> orderItemsDtoList) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderItemsDtoList = orderItemsDtoList;
        this.addressId=addressId;
        this.paymentId=paymentId;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItemsDto> getOrderItemsDtoList() {
        return orderItemsDtoList;
    }

    public void setOrderItemsDtoList(List<OrderItemsDto> orderItemsDtoList) {
        this.orderItemsDtoList = orderItemsDtoList;
    }
}
