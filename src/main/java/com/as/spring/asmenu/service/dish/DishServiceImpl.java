package com.as.spring.asmenu.service.dish;

import com.as.spring.asmenu.repository.DishRepository;
import com.as.spring.asmenu.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService{

    private DishRepository dishRepository;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository){
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }
}
