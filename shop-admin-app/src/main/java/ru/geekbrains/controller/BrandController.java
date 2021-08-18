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
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.service.CommonService;

import javax.validation.Valid;

@Controller
@RequestMapping("/brand")
public class BrandController {

    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    private final CommonService<Brand> brandService;

    @Autowired
    public BrandController(CommonService<Brand> brandService) {
        this.brandService = brandService;
    }

    @GetMapping()
    public String listPage(Model _model){
        logger.info("Brand page requested");

        _model.addAttribute("brands", brandService.findAll());

        return "brands";
    }

    @GetMapping("/new")
    public String registerNewBrand(Model model){
        logger.info("New brand registration started");

        model.addAttribute("brand", new Brand());

        return "brand_form";
    }

    @PostMapping
    public String update(@Valid Brand _brand, BindingResult result){
        logger.info(String.format("Update brand id - %s, name - %s", _brand.getId(), _brand.getName()));

        if(result.hasErrors()){
            logger.info(String.format("Some of parameter values are incorrect id-%s, name - %s", _brand.getId(), _brand.getName()));

            return "brand_form";
        }

        brandService.save(_brand);

        return "redirect:/brand";
    }

    @GetMapping("/{id}")
    public String editBrand(@PathVariable("id") Long _id, Model _model){
        logger.info(String.format("Edit brand request for id - %s", _id));

        _model.addAttribute("brand", brandService.findById(_id)
                .orElseThrow(() -> new NotFoundException(String.format("Brand with id - %s not exists.", _id))));

        return "brand_form";
    }

    @DeleteMapping("/{id}")
    public String brandRemove(@PathVariable("id") Long _id){
        logger.info(String.format("Going to delete brand with id - %s", _id));

        brandService.deleteById(_id);

        return "redirect:/brand";
    }

    @ExceptionHandler
    public ModelAndView NotFoundExceptionHandler(NotFoundException _exception){
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", _exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        return modelAndView;
    }

}
