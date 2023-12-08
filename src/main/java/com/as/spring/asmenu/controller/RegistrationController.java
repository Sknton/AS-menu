package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.model.User;
import com.as.spring.asmenu.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private Logger logger = Logger.getLogger(getClass().getName());

    private final UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {

        theModel.addAttribute("user", new User());

        return "register/registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("user") User theWebUser,
            BindingResult theBindingResult,
            HttpSession session, Model theModel) {

        String userName = theWebUser.getUsername();
        logger.info("Processing registration form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()){
            return "register/registration-form";
        }

        // check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
            theModel.addAttribute("user", new User());
            theModel.addAttribute("errorMessage", "User name already exists.");

            logger.warning("User name already exists.");
            return "register/registration-form";
        }



        // create user account and store in the databse
        userService.saveNew(theWebUser);


        logger.info("Successfully created user: " + userName);

        // place user in the web http session for later use
        session.setAttribute("user", theWebUser);

        return "register/registration-confirmation";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("messageActivation", "Email successfully confirmed");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("messageActivation", "Activation code is not found!");
        }

        return "login/fancy-login";
    }
}

