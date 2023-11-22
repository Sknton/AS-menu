package com.as.spring.asmenu.service.basket_dish;

import com.as.spring.asmenu.model.basket_dish.BasketDish;

import java.util.List;

public interface BasketDishService {
    void setQuantityOfDishInBasket(Long dishId, Long basketId, Integer quantity);

    List<BasketDish> findAll();


}
