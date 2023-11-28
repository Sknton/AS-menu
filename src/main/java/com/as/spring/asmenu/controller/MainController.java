package com.as.spring.asmenu.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final HttpServletRequest request;



    @GetMapping("/")
    public String showHome(Model model) {

        model.addAttribute("httpServletRequest", request);

        return "index";
    }

    @GetMapping("/about-us")
    public String showAboutUs(Model model){

        model.addAttribute("httpServletRequest", request);

        return "about-us";
    }
}
