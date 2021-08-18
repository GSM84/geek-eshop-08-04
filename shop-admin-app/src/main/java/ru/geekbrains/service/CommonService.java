package ru.geekbrains.service;

import java.util.List;
import java.util.Optional;

public interface CommonService<T>{

    List<T> findAll();

    void save(T object);

    Optional<T> findById(Long id);

    void deleteById(Long id);

}
