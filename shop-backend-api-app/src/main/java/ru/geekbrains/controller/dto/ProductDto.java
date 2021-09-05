package ru.geekbrains.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private Long id;

    private String title;

    private BigDecimal price;

    private Category category;

    private Brand brand;

    private List<Long> pictures;

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

}
