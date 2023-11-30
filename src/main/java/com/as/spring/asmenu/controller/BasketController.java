package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.Order;
import com.as.spring.asmenu.model.User;
import com.as.spring.asmenu.service.basket.BasketService;
import com.as.spring.asmenu.service.order.OrderService;
import com.as.spring.asmenu.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final HttpServletRequest request;
    private final UserService userService;
    private final OrderService orderService;

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


    @PostMapping("makeOrder")
    public String makeOrder(@Valid @ModelAttribute("order")Order order,
                            BindingResult theBindingResult,
                            @RequestParam("userId") Long userId,
                            Model model){

        User user = userService.findById(userId);
        order.setUser(user);
        // form validation
        if (theBindingResult.hasErrors()){
            model.addAttribute("httpServletRequest", request);
            model.addAttribute("basket", user.getBasket());
            model.addAttribute("apiKey", googleApiKey);
            return "basket";
        }
        orderService.save(order);
        return "redirect:/basket/"+ user.getBasket().getId() + "?modal";
    }

}

