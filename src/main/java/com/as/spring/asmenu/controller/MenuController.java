package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.model.Basket;
import com.as.spring.asmenu.service.dish.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController {
    private DishService dishService;

    @Autowired
    public MenuController(DishService dishService){
        this.dishService= dishService;
    }

    @GetMapping("/menu")
    public String getMenu(Model model){
        model.addAttribute("dishes", dishService.findAll());
        return "menu";
    }
}
