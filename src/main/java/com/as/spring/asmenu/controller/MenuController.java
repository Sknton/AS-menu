package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.service.dish.DishService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MenuController {
    private final DishService dishService;

    private final HttpServletRequest request;


    @GetMapping("/menu")
    public String getMenu(Model model){

        model.addAttribute("httpServletRequest", request);

        model.addAttribute("dishes", dishService.findAll());
        return "menu";
    }
}
