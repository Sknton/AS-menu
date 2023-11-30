package com.as.spring.asmenu.service.dish;

import com.as.spring.asmenu.model.Dish;
import com.as.spring.asmenu.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService{

    private final DishRepository dishRepository;

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public Set<String> getUniqueDishTypes() {
        List<Dish> dishes = dishRepository.findAll();

        Set<String> types = new HashSet<>();
        for (Dish dish : dishes) {
            types.add(dish.getType());
        }

        return types;
    }
}
