package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.service.basket.BasketService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/basket")
public class BasketController {

    private BasketService basketService;
    private HttpServletRequest request;




    @Autowired
    public BasketController(BasketService basketService, HttpServletRequest request) {
        this.basketService = basketService;
        this.request=request;
    }

    @PostMapping("/addToBasket")
    public String addToBasket(@RequestParam("basketId") Long basketId,
                              @RequestParam("dishId") Long dishId,
                              @RequestParam("quantity") Integer quantity){

        basketService.addToBasket(basketId, dishId, quantity);

        return "redirect:/menu";
    }

    @GetMapping("/{basketId}")
    public String getBasketById(@PathVariable Long basketId, Model model, Principal principal) {
        Basket basket = basketService.findById(basketId);

        // Check if the authenticated user is the owner of the basket
        if (!basket.getUser().getUsername().equals(principal.getName())) {
            return "redirect:/access-denied";
        }

        model.addAttribute("httpServletRequest", request);
        model.addAttribute("basket", basket);
        return "basket";
    }

    @PostMapping("/delete")
    public String deleteDish(@RequestParam("basketId") Long basketId,
                             @RequestParam("dishId") Long dishId,
                             @RequestParam("quantity") Integer quantity){
        basketService.deleteFromBasket(basketId, dishId, quantity);

        return "redirect:/basket/"+basketId;
    }
}

