package com.vintedge.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = true)
    private String image;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Integer discount;

    @Column(nullable = false)
    private Boolean discounted;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Boolean available;

    @Column(nullable = false)
    private Double rating;

    // Costruttori, Getter e Setter
    public Product() {
    }

    public Product(String name, String description, String image, Category category, Double price, Integer discount,
            Boolean discounted, Integer quantity, Boolean available, Double rating) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.discounted = discounted;
        this.quantity = quantity;
        this.available = available;
        this.rating = rating;
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Boolean getDiscounted() {
        return discounted;
    }

    public void setDiscounted(Boolean discounted) {
        this.discounted = discounted;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}

enum Category {
    ELECTRONICS,
    CLOTHING,
    HOME,
    BOOKS,
    FORNITURE,
    HOBBIES
}