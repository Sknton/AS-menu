package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.service.basket_dish.BasketDishService;
import com.as.spring.asmenu.service.dish.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController {
    private DishService dishService;
    private BasketDishService basketDishService;

    @Autowired
    public MenuController(DishService dishService, BasketDishService basketDishService){
        this.dishService= dishService;
        this.basketDishService = basketDishService;
    }

    @GetMapping("/menu")
    public String getMenu(Model model){
        System.out.println(basketDishService.findAll() + "\n");

        model.addAttribute("dishes", dishService.findAll());
        return "menu";
    }
}
