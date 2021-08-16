package ru.geekbrains.service;

import ru.geekbrains.persist.Category;

import java.util.List;
import java.util.Optional;

public interface CategorySerrvice {

    List<Category> findAll();

    void save(Category category);

    Optional<Category> findById(Long id);

    void deleteById(Long id);
}
