package com.impetus.ogos.productmodule.Dto;

import java.util.List;

public class ProductListDto {
    private List<ProductDto> productList;
    private int totalPages;
    private int currentPage;


    public ProductListDto(){}
    public ProductListDto(List<ProductDto> productList, int totalPages,int currentPage) {
        this.productList = productList;
        this.totalPages = totalPages;
        this.currentPage=currentPage;
    }

    public List<ProductDto> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDto> productList) {
        this.productList = productList;
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
