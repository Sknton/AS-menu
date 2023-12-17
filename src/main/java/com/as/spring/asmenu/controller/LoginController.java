package com.as.spring.asmenu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/log-in")
    public String showLoginPage() {

        return "/login/log-in";
    }

    // add request mapping for /access-denied

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {

        return "/login/access-denied";
    }

}
