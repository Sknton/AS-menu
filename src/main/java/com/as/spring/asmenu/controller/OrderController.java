package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.Order;
import com.as.spring.asmenu.model.User;
import com.as.spring.asmenu.service.order.OrderService;
import com.as.spring.asmenu.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final HttpServletRequest request;

    private final UserService userService;


    @PostMapping
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
            return "basket";
        }
        orderService.save(order);
        return "redirect:/basket/"+ user.getBasket().getId() + "?modal";
    }


}
