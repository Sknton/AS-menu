package com.as.spring.asmenu.service.dish;

import com.as.spring.asmenu.model.Dish;
import com.as.spring.asmenu.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService{

    private final DishRepository dishRepository;


    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }
}
