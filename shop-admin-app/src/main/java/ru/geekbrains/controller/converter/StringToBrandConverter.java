package ru.geekbrains.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.geekbrains.persist.model.Brand;

@Component
public class StringToBrandConverter implements Converter<String, Brand> {

    @Override
    public Brand convert(String s) {
        String[] arr = s.split(";");
        return new Brand(Long.parseLong(arr[0]), arr[1]);
    }
}
