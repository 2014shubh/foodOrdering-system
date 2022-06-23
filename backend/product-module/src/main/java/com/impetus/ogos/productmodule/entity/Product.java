package com.impetus.ogos.productmodule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.impetus.ogos.productmodule.Dto.ProductDto;


import javax.persistence.*;


@Entity
    @Table(name = "products")
    public class Product{

        @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;

        private  String name;
        private  String imageURL;
        private  double price;
        private  String description;
        private double productWeight;
        private String measurementUnit;

        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "category_id", nullable = false)
        Category category;



       // @JsonIgnore
     //   @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
     //   private List<Cart> carts;





        public Product(ProductDto productDto, Category category) {
            this.name = productDto.getName();
            this.imageURL = productDto.getImageURL();
            this.description = productDto.getDescription();
            this.price = productDto.getPrice();
            this.productWeight=productDto.getProductWeight();
            this.measurementUnit=productDto.getMeasurementUnit();
            this.category = category;
        }

        public Product(String name, String imageURL, double price, String description, double productWeight, String measurementUnit, Category category) {
            super();
            this.name = name;
            this.imageURL = imageURL;
            this.price = price;
            this.description = description;
            this.productWeight=productWeight;
            this.measurementUnit=measurementUnit;
            this.category = category;
        }

        public Product() {
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

        public void setProductWeight (double productWeight) {
            this.productWeight = productWeight;
        }

        public double getProductWeight() {
            return productWeight;
        }
        public void setMeasurementUnit (String measurementUnit) {
            this.productWeight = productWeight;
        }

        public String getMeasurementUnit() {
            return measurementUnit;
        }




        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", imageURL='" + imageURL + '\'' +
                    ", price=" + price +
                    ", productWeight=" + productWeight +
                    ", measurementUnit=" + measurementUnit +
                    ", description='" + description + '\'' +
                    '}';
        }
}
