package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.dto.RoleDto;
import ru.geekbrains.persist.RoleRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService implements CommonService<RoleDto> {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(RoleDto object) {

    }

    @Override
    public Optional<RoleDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
