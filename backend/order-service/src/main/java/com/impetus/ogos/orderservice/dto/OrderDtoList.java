package com.impetus.ogos.orderservice.dto;

import java.util.List;

public class OrderDtoList {
    private List<OrderDto> orderList;
    private int totalPages;
    private int currentPage;


    public OrderDtoList(){}
    public OrderDtoList(List<OrderDto> orderList, int totalPages,int currentPage) {
        this.orderList = orderList;
        this.totalPages = totalPages;
        this.currentPage=currentPage;
    }

    public List<OrderDto> getOrderList() {
        return orderList;
    }

    public void setProductList(List<OrderDto> orderList) {
        this.orderList = orderList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}


