package com.as.spring.asmenu.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private HttpServletRequest request;

    @Autowired
    public MainController(HttpServletRequest request){
        this.request = request;
    }

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
