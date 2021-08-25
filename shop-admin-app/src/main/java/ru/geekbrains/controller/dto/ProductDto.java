package ru.geekbrains.controller.dto;

import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;

import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    private Long id;

    private String title;

    private BigDecimal price;

    private Category category;

    private Brand brand;

    private List<Long> pictures;

    private MultipartFile[] newPictures;

    public ProductDto() {
    }

    public ProductDto(Long id, String title, BigDecimal price, Category category, Brand brand) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }

    public ProductDto(Long id, String title, BigDecimal price, Category category, Brand brand, List<Long> pictures) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
        this.brand = brand;
        this.pictures = pictures;
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

    public MultipartFile[] getNewPictures() {
        return newPictures;
    }

    public void setNewPictures(MultipartFile[] newPictures) {
        this.newPictures = newPictures;
    }

    public List<Long> getPictures() {
        return pictures;
    }

    public void setPictures(List<Long> pictures) {
        this.pictures = pictures;
    }
}
