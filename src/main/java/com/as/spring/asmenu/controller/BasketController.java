package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.service.basket.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping("/addToBasket")
    public String addToBasket(@RequestParam("dishId") Long dishId, @RequestParam("basketId") Long basketId) {
        try {
            basketService.addToBasket(dishId, basketId);
        }catch (Exception e){
            return "redirect:/menu?error";
        }
        return "redirect:/menu";
    }

    @GetMapping("/{basketId}")
    public String getBasketById(@PathVariable long basketId, Model model){
        Basket basket = basketService.findBasketById(basketId);
        model.addAttribute("basket", basket);
        return "basket";
    }
}

