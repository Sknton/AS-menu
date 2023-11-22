package com.as.spring.asmenu.dao;

import com.as.spring.asmenu.model.basket_dish.BasketDish;
import com.as.spring.asmenu.model.basket_dish.BasketDishId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketDishRepository extends JpaRepository<BasketDish, BasketDishId> {


}
