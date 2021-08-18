package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.BrandRepository;
import ru.geekbrains.persist.model.Brand;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService implements CommonService<Brand> {

    private BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public void save(Brand _brand) {
        brandRepository.save(_brand);
    }

    @Override
    public Optional<Brand> findById(Long _id) {
        return brandRepository.findById(_id);
    }

    @Override
    public void deleteById(Long _id) {
        brandRepository.deleteById(_id);
    }
}
