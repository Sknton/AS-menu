package com.as.spring.asmenu.controller;


import com.as.spring.asmenu.model.User;
import com.as.spring.asmenu.repository.RoleRepository;
import com.as.spring.asmenu.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final HttpServletRequest request;

    private final RoleRepository roleRepository;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roles")
    public String userList(Model model) {


        model.addAttribute("httpServletRequest", request);

        model.addAttribute("users", userService.findAll());

        model.addAttribute("allRoles", roleRepository.findAll());

        return "/admin/user-list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/roles/{id}")
    public String updateUserRoles(@PathVariable Long id,
                                  @RequestParam(value = "roles", required = false) List<String> roles) {
        if (roles == null) {
            return "redirect:/users/roles";
        }
        User user = userService.findById(id);
        // Clear the existing roles
        user.getRoles().clear();
        for (String roleName : roles) {
            user.add(roleRepository.findRoleByName(roleName));
        }
        userService.save(user);
        return "redirect:/users/roles";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/roles/delete")
    public String deleteByAdmin(@RequestParam("userId") Long id) {
        userService.deleteById(id);
        return "redirect:/users/roles";
    }


    @GetMapping("/{userId}")
    public String editUser(@PathVariable("userId") Long  userId, Model model){
        User user = userService.findById(userId);

        model.addAttribute("user", user);

        model.addAttribute("httpServletRequest", request);

        return "/edit-user";
    }

    @PostMapping("/{userId}")
    public String saveUser(@PathVariable("userId") Long userId,
                           @Valid @ModelAttribute("user") User user,
                           BindingResult bindingResult,
                           Model model){

        model.addAttribute("httpServletRequest", request);

        User existingUser = userService.findById(userId);
        User userWithSameUsername = userService.findByUserName(user.getUsername());

        if (userWithSameUsername != null && !userWithSameUsername.getId().equals(userId)){
            model.addAttribute("user", existingUser);
            model.addAttribute("errorMessage", "User name already exists.");
            return "edit-user";
        }

        if (bindingResult.hasErrors()){
            return "edit-user";
        }


        existingUser.setUsername(user.getUsername());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        String email = user.getEmail();
        if (!email.equals(existingUser.getEmail())){
            existingUser.setEmail(email);
            userService.saveWithNewEmail(existingUser);
            model.addAttribute("successMessage", "Pleas, visit your email to confirm it");
            return "edit-user";
        }
        userService.save(existingUser);
        model.addAttribute("successMessage", "Changes saved");

        return "edit-user";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("userId") Long id) {
        userService.deleteById(id);
        return "/login/log-in";
    }
}
