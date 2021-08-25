package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.NotFoundException;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.controller.param.ProductListParams;
import ru.geekbrains.persist.*;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements CommonPagebleService<ProductDto, ProductListParams>{

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    private final PictureService pictureService;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, PictureService pictureService, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.pictureService = pictureService;
        this.brandRepository = brandRepository;
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
                            , product.getPictures().stream().map(Picture::getId).collect(Collectors.toList())
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
                           , product.getPictures().stream().map(Picture::getId).collect(Collectors.toList())
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
        pictureService.getFileList(pictureService.getPicturesByProduct(_id)).forEach(File::delete);

        productRepository.deleteById(_id);
    }

    @Override
    @Transactional
    public void save(ProductDto productDto) {
        Product product = (productDto.getId() != null) ? productRepository.findById(productDto.getId())
                .orElseThrow(() -> new NotFoundException("Product not found")) : new Product();

        Category category = categoryRepository.findById(productDto.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setTitle(productDto.getTitle());
        product.setCategory(category);
        product.setBrand(productDto.getBrand());
        product.setPrice(productDto.getPrice());

        if (productDto.getNewPictures() != null) {
            Arrays.stream(productDto.getNewPictures())
                    .filter(pic -> pic.getSize() > 0).forEach(newPicture -> {
                        try {
                            product.getPictures().add(new Picture(null,
                                    newPicture.getOriginalFilename(),
                                    newPicture.getContentType(),
                                    pictureService.CreatePicture(newPicture.getBytes()),
                                    product
                            ));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });
        }

        productRepository.save(product);
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
