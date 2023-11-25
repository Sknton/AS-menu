package com.as.spring.asmenu.service.basket;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.Dish;

import java.util.List;

public interface BasketService {

    void addToBasket(Long basketId, Long dishId, Integer quantity);

    Basket findById(Long id);

    void deleteFromBasket(Long basketId, Long dishId, Integer quantity);
}
