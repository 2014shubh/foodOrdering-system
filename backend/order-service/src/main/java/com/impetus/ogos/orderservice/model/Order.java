package com.impetus.ogos.orderservice.model;

import com.impetus.ogos.orderservice.model.enumeration.OrderStage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private LocalDateTime timeOfOrder;
    private LocalDateTime stageDate;

    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStage stage;
   private int feedback;
private  String userId;
private  int addressId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }




    private String paymentID;

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    @OneToMany(cascade = CascadeType.ALL)

    private List<OrderItems> orderDetails;



    public List<OrderItems> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderItems> orderDetails) {
        this.orderDetails = orderDetails;
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


    public LocalDateTime getStageDate() {
        return stageDate;
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
    
    


    public int getFeedback() {
		return feedback;
	}

	public void setFeedback(int feedback) {
		this.feedback = feedback;
	}

	public OrderStage getStage() {
        return stage;
    }

    public void setStage(OrderStage stage) {
        this.stage = stage;
    }


    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Order(int orderId, double totalPrice, String pending, String userId, int addressId,String paymentID) {
        this.orderId = orderId;
        this.timeOfOrder = timeOfOrder;
        this.stageDate = stageDate;
        this.totalPrice = totalPrice;
        this.stage = stage;
        this.userId = userId;
        this.paymentID = paymentID;
        this.orderDetails = orderDetails;
        this.addressId=addressId;
    }

    public Order() {

    }
}
