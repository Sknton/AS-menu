package com.as.spring.asmenu.service.basket;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.Dish;

import java.util.List;

public interface BasketService {

    void addToBasket(Long dishId, Long basketId, Integer quantity);

    Basket findById(Long id);

}
