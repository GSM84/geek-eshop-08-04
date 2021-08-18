package ru.geekbrains.controller.dto;

import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;

    private String title;

    private BigDecimal price;

    private Category category;

    private Brand brand;

    public ProductDto() {
    }

    public ProductDto(Long id, String title, BigDecimal price, Category category, Brand brand) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
