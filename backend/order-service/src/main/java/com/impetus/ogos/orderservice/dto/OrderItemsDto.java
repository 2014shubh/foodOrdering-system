package com.impetus.ogos.orderservice.dto;

import javax.validation.constraints.NotNull;

public class OrderItemsDto {

    private @NotNull int quantity;
    private  @NotNull double purchasePrice;

    private @NotNull int productId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public @NotNull String productName;

    public OrderItemsDto(int quantity, double purchasePrice, int productId,String productName) {
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.productId = productId;
        this.productName=productName;
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

    public OrderItemsDto() {

    }
}
