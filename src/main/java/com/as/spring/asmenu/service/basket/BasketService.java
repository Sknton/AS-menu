package com.as.spring.asmenu.service.basket;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.Dish;

import java.util.List;

public interface BasketService {

    Basket findById(Long id);

    void save(Basket basket);

    void addToBasket(Long basketId, Long dishId, Integer quantity);

    void deleteFromBasket(Long basketId, Long dishId, Integer quantity);
}
