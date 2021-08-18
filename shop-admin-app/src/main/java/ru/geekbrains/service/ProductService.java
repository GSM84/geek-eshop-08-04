package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.controller.param.ProductListParams;
import ru.geekbrains.persist.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Page<Product> findWithFilter(ProductListParams _params);

    Optional<ProductDto> findById(Long id);

    void deleteById(Long id);

    void save(ProductDto product);

    List<Product> findAll();
}
