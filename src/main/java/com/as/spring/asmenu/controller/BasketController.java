package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.basket_dish.BasketDish;
import com.as.spring.asmenu.service.basket.BasketService;
import com.as.spring.asmenu.service.basket_dish.BasketDishService;
import com.as.spring.asmenu.service.dish.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basket")
public class BasketController {

    private BasketService basketService;

    private BasketDishService basketDishService;

    private DishService dishService;


    @Autowired
    public BasketController(BasketService basketService, BasketDishService basketDishService, DishService dishService) {
        this.basketService = basketService;
        this.basketDishService = basketDishService;
        this.dishService = dishService;
    }

    @PostMapping("/addToBasket")
    public String addToBasket(@RequestParam("dishId") Long dishId,
                              @RequestParam("basketId") Long basketId,
                              @RequestParam("quantity") Integer quantity){

        basketService.addToBasket(dishId, basketId, quantity);
        System.out.println(basketDishService.findAll());
        basketDishService.setQuantityOfDishInBasket(dishId, basketId, quantity);
        System.out.println(basketDishService.findAll());


        return "redirect:/menu";
    }

    @GetMapping("/{basketId}")
    public String getBasketById(@PathVariable long basketId, Model model) {
        Basket basket = basketService.findBasketById(basketId);
        model.addAttribute("basket", basket);
        List<BasketDish> basketDishes = basketDishService.findAll();
        model.addAttribute("basketDishes", basketDishes);
        return "basket";
    }
}

