package com.as.spring.asmenu.repository;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.BasketDish;
import com.as.spring.asmenu.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketDishRepository extends JpaRepository<BasketDish, Long> {

    BasketDish findByBasketAndDish(Basket basket, Dish dish);

}
