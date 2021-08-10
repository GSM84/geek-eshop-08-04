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
import ru.geekbrains.service.RoleService;
import ru.geekbrains.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listPage(Model model,
                           UserListParams userListParams) {
        logger.info("User list page requested");

        model.addAttribute("users", userService.findWithFilter(userListParams));
        return "users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        logger.info("New user page requested");

        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", new UserDto());
        return "user_form";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        logger.info("Edit user page requested");

        UserDto user = userService.findById(id).get();

        model.addAttribute("user", userService.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
        model.addAttribute("roles", roleService.findAll());

        return "user_form";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        logger.info("Saving user");

        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findAll());
            return "user_form";
        }

        if (!user.getPassword().equals(user.getRepeatPassword())) {
            result.rejectValue("repeatPassword", "", "Repeated password doesn't match with password or empty.");
            model.addAttribute("roles", roleService.findAll());
            return "user_form";
        } else if (user.getPassword().isBlank() || user.getPassword().isEmpty()) {
            result.rejectValue("password", "", "Password can't be empty.");
            model.addAttribute("roles", roleService.findAll());
            return "user_form";
        }

        userService.save(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        logger.info("Deleting user with id {}", id);

        userService.deleteById(id);
        return "redirect:/user";
    }

    //@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}