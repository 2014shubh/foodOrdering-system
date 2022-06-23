package com.impetus.ogos.dto;

public class InventoryProductDto {

    private Integer id;
    private  String name;
    private  double price;
    private  String description;
    private double productWeight;
    private String measurementUnit;
    private Integer categoryId;
	private Integer quantity;
	
	
	public InventoryProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public InventoryProductDto(Integer id, String name, double price, String description, double productWeight,
			String measurementUnit, Integer categoryId, Integer quantity) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.productWeight = productWeight;
		this.measurementUnit = measurementUnit;
		this.categoryId = categoryId;
		this.quantity = quantity;
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
	
	
}
