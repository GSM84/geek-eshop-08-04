package ru.geekbrains.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.controller.param.ProductListParams;
import ru.geekbrains.service.CommonPagebleService;


@RequestMapping("/product")
@RestController
public class ProductController {

    private final CommonPagebleService<ProductDto, ProductListParams> productService;

    @Autowired
    public ProductController(CommonPagebleService<ProductDto, ProductListParams> _prodcutService) {
        this.productService = _prodcutService;
    }

    @GetMapping("/all")
    public Page<ProductDto> findAll(ProductListParams _params){
        return productService.findWithFilter(_params);
    }

}
