package ru.geekbrains.controller;

import ru.geekbrains.persist.Category;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;

    private String title;

    private BigDecimal price;

    private Category category;

    public ProductDto() {
    }

    public ProductDto(Long id, String title, BigDecimal price, Category category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
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
}
