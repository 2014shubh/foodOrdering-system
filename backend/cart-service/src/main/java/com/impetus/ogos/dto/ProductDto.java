package com.impetus.ogos.dto;

public class ProductDto 
{
    private Integer id;
    private String name;
    private String imageURL;
    private double price;
    private String description;
    private double productWeight;
    private String measurementUnit;
    private Integer categoryId;
    
    
	public ProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ProductDto(Integer id, String name, String imageURL, double price, String description, double productWeight,
			String measurementUnit, Integer categoryId) {
		super();
		this.id = id;
		this.name = name;
		this.imageURL = imageURL;
		this.price = price;
		this.description = description;
		this.productWeight = productWeight;
		this.measurementUnit = measurementUnit;
		this.categoryId = categoryId;
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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getProductWeight() {
		return productWeight;
	}


	public void setProductWeight(double productWeight) {
		this.productWeight = productWeight;
	}


	public String getMeasurementUnit() {
		return measurementUnit;
	}


	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}


	public Integer getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
    
    
  
}

