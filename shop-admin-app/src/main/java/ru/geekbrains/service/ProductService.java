package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.controller.param.ProductListParams;
import ru.geekbrains.persist.*;
import ru.geekbrains.persist.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements CommonPagebleService<ProductDto, ProductListParams>{

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> findWithFilter(ProductListParams _params) {
        Specification<Product> spec = Specification.where(null);
        if(_params.getMinPrice() != null){
            spec = spec.and(ProductSpecification.minPrice(_params.getMinPrice()));
        }
        if(_params.getMaxPrice() != null){
            spec = spec.and(ProductSpecification.maxPrice(_params.getMaxPrice()));
        }
        if(_params.getSortType() != null && !_params.getSortType().isBlank() && _params.getSortField() != null && !_params.getSortField().isBlank()) {

            return productRepository.findAll(spec, PageRequest.of(
                    Optional.ofNullable(_params.getPageNum()).orElse(1) - 1
                    , Optional.ofNullable(_params.getPageSize()).orElse(7)
                    , Sort.by(Sort.Direction.fromString(_params.getSortType()), _params.getSortField())))
                    .map(product -> new ProductDto(
                            product.getId()
                            , product.getTitle()
                            , product.getPrice()
                            , product.getCategory()
                            , product.getBrand()
                    ));
        } else {
           return productRepository.findAll(spec, PageRequest.of(
                    Optional.ofNullable(_params.getPageNum()).orElse(1) - 1
                    , Optional.ofNullable(_params.getPageSize()).orElse(7)))
                   .map(product -> new ProductDto(
                           product.getId()
                           , product.getTitle()
                           , product.getPrice()
                           , product.getCategory()
                           , product.getBrand()
                   ));
        }

    }

    @Override
    public Optional<ProductDto> findById(Long _id) {
        return productRepository.findById(_id).map(product -> new ProductDto(
                  product.getId()
                , product.getTitle()
                , product.getPrice()
                , product.getCategory()
                , product.getBrand())
        );
    }

    @Override
    public void deleteById(Long _id) {
        productRepository.deleteById(_id);
    }

    @Override
    public void save(ProductDto _product) {
        Product prod = new Product(_product.getId(), _product.getTitle(), _product.getPrice(), _product.getCategory(), _product.getBrand());

        productRepository.save(prod);
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(product -> new ProductDto(
                        product.getId()
                        , product.getTitle()
                        , product.getPrice()
                        , product.getCategory()
                        , product.getBrand()
                ))
                .collect(Collectors.toList());
    }

}
