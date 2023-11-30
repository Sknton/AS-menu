package com.as.spring.asmenu.service.dish;

import com.as.spring.asmenu.model.Dish;

import java.util.List;
import java.util.Set;

public interface DishService {
    List<Dish> findAll();

    Set<String> getUniqueDishTypes();
}
