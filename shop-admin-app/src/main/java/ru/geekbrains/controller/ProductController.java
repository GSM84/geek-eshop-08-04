package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.controller.param.ProductListParams;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.service.CommonPagebleService;
import ru.geekbrains.service.CommonService;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final CommonPagebleService<ProductDto, ProductListParams> productService;

    private final CommonService<Category> categoryService;

    private final CommonService<Brand> brandService;

    @Autowired
    public ProductController(CommonPagebleService<ProductDto, ProductListParams> _prodcutService, CommonService<Category> categorySerrvice, CommonService<Brand> brandService) {
        this.productService = _prodcutService;
        this.categoryService = categorySerrvice;
        this.brandService = brandService;
    }

    @GetMapping()
    public String listPage(ProductListParams _params
                         , Model _model){
        logger.info(String.format("Product list page requested with minPrice - %s, maxPrice - %s, sort type %s"
                , _params.getMinPrice(), _params.getMaxPrice(), _params.getSortType()));

        if(_params.getSortType() != null && !_params.getSortType().isBlank()
                && _params.getSortField() != null && !_params.getSortField().isBlank()) {
            _model.addAttribute("sortType", _params.getSortType().equals("asc") ? "desc" : "asc");
        } else {
            _model.addAttribute("sortType", "asc");
        }

        _model.addAttribute("products", productService.findWithFilter(_params));

        return "products";
    }

    @GetMapping("/new")
    public String newProductForm(Model _model){
        logger.info("New product page requested.  " + categoryService.findAll().size());

        _model.addAttribute("categories", categoryService.findAll());
        _model.addAttribute("brands", brandService.findAll());
        _model.addAttribute("product", new ProductDto());

        return "product_form";
    }

    @PostMapping
    public String update(@Valid ProductDto _product, BindingResult result){
        logger.info(String.format("Update product id-%s, title - %s, price - %s, category - %s, brand_id - %s, name - %s",
                _product.getId(),
                _product.getTitle(),
                _product.getPrice(),
                _product.getCategory().getName(),
                _product.getBrand().getId(),
                _product.getBrand().getName()));

        if(result.hasErrors()){
            logger.info(String.format("Some of parameter values are incorrect id - %s, title - %s, price - %s, cat_id - %s, name - %s, brand_id - %s, name - %s",
                    _product.getId(),
                    _product.getTitle(),
                    _product.getPrice(),
                    _product.getCategory().getId(),
                    _product.getCategory().getName(),
                    _product.getBrand().getId(),
                    _product.getBrand().getName()));

            return "product_form";
        }

        productService.save(_product);

        return "redirect:/product";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long _id, Model _model){
        logger.info(String.format("Edit product request for id - %s", _id));

        _model.addAttribute("categories", categoryService.findAll());
        _model.addAttribute("brands", brandService.findAll());
        _model.addAttribute("product", productService.findById(_id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with id - %s not exists.", _id))));

        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String productRemove(@PathVariable("id") Long _id){
        logger.info(String.format("Going to delete product with id - %s", _id));

        productService.deleteById(_id);

        return "redirect:/product";
    }

    @ExceptionHandler
    public ModelAndView NotFoundExceptionHandler(NotFoundException _exception){
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", _exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        return modelAndView;
    }

}
