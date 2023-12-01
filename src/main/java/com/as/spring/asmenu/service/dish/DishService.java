package com.as.spring.asmenu.service.dish;

import com.as.spring.asmenu.model.Dish;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface DishService {
    List<Dish> findAll();

    Set<String> getUniqueDishTypes();

    void save(Dish dish, MultipartFile file);

    void deleteById(Long id);
}
