package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.model.Dish;
import com.as.spring.asmenu.service.dish.DishService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final DishService dishService;

    private final HttpServletRequest request;



    @GetMapping
    public String getMenu(Model model){

        model.addAttribute("httpServletRequest", request);

        model.addAttribute("dishes", dishService.findAll());

        model.addAttribute("types", dishService.getUniqueDishTypes());
        return "menu";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/system/add-dish")
    public String showAddDishPage(Model model){
        model.addAttribute("httpServletRequest", request);
        model.addAttribute("dish", new Dish());
        return "common/add-dish";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/system/dish-form")
    public String processDishForm(@Valid @ModelAttribute("dish") Dish dish, BindingResult bindingResult,
                          Model model,
                          @RequestParam("file") MultipartFile imageFile){
        if (bindingResult.hasErrors() || (imageFile.isEmpty() && dish.getFileName().isEmpty())){
            model.addAttribute("httpServletRequest", request);
            return "/common/add-dish";
        }

        dishService.save(dish, imageFile);
        return "redirect:/menu";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/system/delete")
    public String delete(@RequestParam("dishId") Long id){
        dishService.deleteById(id);
        return "redirect:/menu";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/system/dishes/{dishId}")
    public String showEditDishPage(@PathVariable("dishId") Long  dishId, Model model){
        Dish dish = dishService.findById(dishId);

        model.addAttribute("dish", dish);

        model.addAttribute("httpServletRequest", request);

        return "common/add-dish";
    }

}
