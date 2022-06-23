package com.impetus.ogos.dto;

public class CartProductDto {
	
    private Integer id;
    private String name;
    private String imageURL;
    private double price;
    private String description;
    private double productWeight;
    private String measurementUnit;
    private Integer categoryId;
    
    private Integer quantity;
    private Double total;
    
    
    public CartProductDto(ProductDto dto, Integer quantity)
    {
    	this.id = dto.getId();
    	this.name = dto.getName();
    	this.imageURL = dto.getImageURL();
    	this.price = dto.getPrice();
    	this.description = dto.getDescription();
    	this.productWeight = dto.getProductWeight();
    	this.measurementUnit = dto.getMeasurementUnit();
    	this.categoryId = dto.getCategoryId();
    	this.quantity = quantity;
    	this.total = quantity * dto.getPrice();
    	
    }
    

	public CartProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CartProductDto(Integer id, String name, String imageURL, double price, String description,
			double productWeight, String measurementUnit, Integer categoryId, Integer quantity, Double total) {
		super();
		this.id = id;
		this.name = name;
		this.imageURL = imageURL;
		this.price = price;
		this.description = description;
		this.productWeight = productWeight;
		this.measurementUnit = measurementUnit;
		this.categoryId = categoryId;
		this.quantity = quantity;
		this.total = total;
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


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Double getTotal() {
		return total;
	}


	public void setTotal(Double total) {
		this.total = total;
	}
    
    

}
