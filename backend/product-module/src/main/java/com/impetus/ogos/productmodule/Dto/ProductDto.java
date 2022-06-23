package com.impetus.ogos.productmodule.Dto;

import com.impetus.ogos.productmodule.entity.Product;

public class ProductDto {
    private Integer id;
    private  String name;
    private  String imageURL;
    private  double price;
    private  String description;
    private double productWeight;
    private String measurementUnit;
    private Integer categoryId;


    public ProductDto(Product product) {
        this.setId(product.getId());
        this.setName(product.getName());
        this.setImageURL(product.getImageURL());
        this.setDescription(product.getDescription());
        this.setPrice(product.getPrice());
        this.setMeasurementUnit(product.getMeasurementUnit());
        this.setProductWeight(product.getProductWeight());
        this.setCategoryId(product.getCategory().getId());
    }

    public ProductDto(String name, String imageURL, double price, String measurementUnit,double productWeight, String description, Integer categoryId) {
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
        this.description = description;
        this.measurementUnit=measurementUnit;
        this.productWeight=productWeight;
        this.categoryId = categoryId;
    }

    public ProductDto() {
    }

   public Integer getId() {
       return id;
   }

   public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit= measurementUnit;
    }
    public String getMeasurementUnit() {
        return measurementUnit;
    }
    public void setProductWeight(double productWeight) {
        this.productWeight= productWeight;
    }
    public double getProductWeight() {
        return productWeight;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
