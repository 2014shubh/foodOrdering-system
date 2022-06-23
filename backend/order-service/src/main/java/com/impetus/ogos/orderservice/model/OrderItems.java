package com.impetus.ogos.orderservice.model;


import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderDetailsId;
    private @NonNull int quantity;
    private @NonNull double purchasePrice;
    private  @NonNull int productId;
    private  @NonNull String productName;

    public int getOrderDetailsId() {
        return orderDetailsId;
    }

    @ManyToOne
   @JoinColumn(name = "orderId")
    private Order order;

    public OrderItems(int quantity, double purchasePrice, int productId, Order order,String productName) {
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.productId = productId;
        this.order = order;
        this.productName=productName;
    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderItems() {

    }
}
