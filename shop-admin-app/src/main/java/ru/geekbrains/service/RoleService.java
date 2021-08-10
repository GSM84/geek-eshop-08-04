package ru.geekbrains.service;

import ru.geekbrains.controller.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> findAll();
}
