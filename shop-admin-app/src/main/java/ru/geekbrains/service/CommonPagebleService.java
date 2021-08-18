package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import ru.geekbrains.controller.dto.UserDto;
import ru.geekbrains.controller.param.UserListParams;

import java.util.List;
import java.util.Optional;

public interface CommonPagebleService<T, D> {
    List<T> findAll();

    Page<T> findWithFilter(D _paramList);

    Optional<T> findById(Long id);

    void save(T user);

    void deleteById(Long id);

}
