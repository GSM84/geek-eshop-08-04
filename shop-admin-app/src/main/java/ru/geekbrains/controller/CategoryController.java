package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.service.CommonService;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CommonService<Category> categoryService;

    @Autowired
    public CategoryController(CommonService<Category> categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public String listPage(Model _model){
        logger.info("Categories page requested");

        _model.addAttribute("categories", categoryService.findAll());

        return "categories";
    }

    @GetMapping("/new")
    public String registerNewCategory(Model model){
        logger.info("New category registration started");

        model.addAttribute("category", new Category());

        return "category_form";
    }

    @PostMapping
    public String update(@Valid Category _category, BindingResult result){
        logger.info(String.format("Update category id - %s, name - %s", _category.getId(), _category.getName()));

        if(result.hasErrors()){
            logger.info(String.format("Some of parameter values are incorrect id-%s, name - %s", _category.getId(), _category.getName()));

            return "category_form";
        }

        categoryService.save(_category);

        return "redirect:/category";
    }

    @GetMapping("/{id}")
    public String editCategory(@PathVariable("id") Long _id, Model _model){
        logger.info(String.format("Edit category request for id - %s", _id));

        _model.addAttribute("category", categoryService.findById(_id)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id - %s not exists.", _id))));

        return "category_form";
    }

    @DeleteMapping("/{id}")
    public String categoryRemove(@PathVariable("id") Long _id){
        logger.info(String.format("Going to delete category with id - %s", _id));

        categoryService.deleteById(_id);

        return "redirect:/category";
    }

    @ExceptionHandler
    public ModelAndView NotFoundExceptionHandler(NotFoundException _exception){
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", _exception.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);

        return modelAndView;
    }

}
