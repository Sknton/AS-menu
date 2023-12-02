package com.as.spring.asmenu.controller;


import com.as.spring.asmenu.model.Role;
import com.as.spring.asmenu.model.User;
import com.as.spring.asmenu.repository.RoleRepository;
import com.as.spring.asmenu.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final HttpServletRequest request;

    private final RoleRepository roleRepository;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {


        model.addAttribute("httpServletRequest", request);

        model.addAttribute("users", userService.findAll());

        model.addAttribute("allRoles", roleRepository.findAll());

        return "/admin/user-list";
    }

    @PostMapping("/{id}")
    public String updateUserRoles(@PathVariable Long id,
                                  @RequestParam(value = "roles", required = false) List<String> roles) {
        if (roles == null) {
            return "redirect:/users";
        }
        User user = userService.findById(id);
        // Clear the existing roles
        user.getRoles().clear();
        for (String roleName : roles) {
            user.add(roleRepository.findRoleByName(roleName));
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("userId") Long id){
        userService.deleteById(id);
        return "redirect:/users";
    }


}
