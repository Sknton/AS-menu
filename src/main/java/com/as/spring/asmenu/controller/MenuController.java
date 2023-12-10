package com.as.spring.asmenu.controller;

import com.as.spring.asmenu.model.Dish;
import com.as.spring.asmenu.model.User;
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
    @GetMapping("/systems/addDish")
    public String showAddDishPage(Model model){
        model.addAttribute("httpServletRequest", request);
        model.addAttribute("dish", new Dish());
        return "admin/add-dish";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/systems/addDish")
    public String addDish(@Valid @ModelAttribute("dish") Dish dish, BindingResult bindingResult,
                          Model model,
                          @RequestParam("file") MultipartFile imageFile){
        model.addAttribute("httpServletRequest", request);
        if (bindingResult.hasErrors()){
            return "/admin/add-dish";
        }

        if (imageFile.isEmpty() && dish.getFileName() == null){
            return "/admin/add-dish";
        }

        dishService.save(dish, imageFile);
        return "redirect:/menu";
    }

    @GetMapping("/systems/delete")
    public String delete(@RequestParam("dishId") Long id){
        dishService.deleteById(id);
        return "redirect:/menu";
    }


    @GetMapping("/systems/dishes/{dishId}")
    public String editDish(@PathVariable("dishId") Long  dishId, Model model){
        Dish dish = dishService.findById(dishId);

        model.addAttribute("dish", dish);

        model.addAttribute("httpServletRequest", request);

        return "admin/add-dish";
    }

}
