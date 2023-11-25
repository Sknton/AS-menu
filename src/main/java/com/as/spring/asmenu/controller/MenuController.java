package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.service.dish.DishService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
    private DishService dishService;

    private HttpServletRequest request;

    @Autowired
    public MenuController(DishService dishService, HttpServletRequest request){
        this.dishService= dishService;
        this.request = request;
    }

    @GetMapping("/menu")
    public String getMenu(Model model){

        model.addAttribute("httpServletRequest", request);

        model.addAttribute("dishes", dishService.findAll());
        return "menu";
    }
}
