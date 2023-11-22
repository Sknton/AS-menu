package com.as.spring.asmenu.service.basket;

import com.as.spring.asmenu.model.Basket;

public interface BasketService {
    Basket findBasketById(long id);

    void addToBasket(Long dishId, Long basketId, Integer quantity);
}
