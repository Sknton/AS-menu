package com.as.spring.asmenu.dao;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.BasketDish;
import com.as.spring.asmenu.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketDishRepository extends JpaRepository<BasketDish, Long> {

    BasketDish findByBasketAndDish(Basket basket, Dish dish);

}
