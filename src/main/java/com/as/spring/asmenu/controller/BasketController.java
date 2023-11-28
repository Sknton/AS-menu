package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.Order;
import com.as.spring.asmenu.service.basket.BasketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final HttpServletRequest request;

    @Value("${google.api.key}")
    private String googleApiKey;


    @PostMapping("/addToBasket")
    public String addToBasket(@RequestParam("basketId") Long basketId,
                              @RequestParam("dishId") Long dishId,
                              @RequestParam("quantity") Integer quantity) {

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
        model.addAttribute("apiKey", googleApiKey);
        model.addAttribute("order", new Order());
        return "basket";
    }

    @PostMapping("/delete")
    public String deleteDish(@RequestParam("basketId") Long basketId,
                             @RequestParam("dishId") Long dishId,
                             @RequestParam("quantity") Integer quantity) {
        basketService.deleteFromBasket(basketId, dishId, quantity);

        return "redirect:/basket/" + basketId;
    }

    @PostMapping("/orderIsReady")
    private String orderIsReady(@RequestParam("basketId") Long basketId) {
        Basket basket = basketService.findById(basketId);
        basketService.save(basket);
        return "redirect:/basket/" + basketId;
    }


    @GetMapping("/exam")
    public String exam(Model model){
        model.addAttribute("API_KEY", googleApiKey);
        return "/example/place";
    }
}

