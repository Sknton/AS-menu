package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.model.Order;
import com.as.spring.asmenu.model.User;
import com.as.spring.asmenu.service.mail.MailSender;
import com.as.spring.asmenu.service.order.OrderService;
import com.as.spring.asmenu.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final HttpServletRequest request;

    @PreAuthorize("hasAuthority('DELIVERY')")
    @GetMapping
    public String showOrdersPage(Model model){
        model.addAttribute("httpServletRequest", request);
        model.addAttribute("orders", orderService.findAll());
        return "delivery/orders";
    }


    @PreAuthorize("hasAuthority('DELIVERY')")
    @GetMapping("/{id}")
    public String showOrderDetailsPage(@PathVariable("id") Long orderId, Model model){
        Order order = orderService.findById(orderId);
        model.addAttribute("httpServletRequest", request);
        model.addAttribute("order", order);

        return "/delivery/order-details";
    }

    @PreAuthorize("hasAuthority('DELIVERY')")
    @GetMapping("/delivered")
    public String deliveryConfirmation(@RequestParam("orderId") Long orderId){
        orderService.orderIsDelivered(orderId);
        return "redirect:/orders";
    }


}
