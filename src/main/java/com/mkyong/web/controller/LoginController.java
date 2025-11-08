package com.mkyong.web.controller;


import com.mkyong.web.model.User;
import com.mkyong.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping
    public String loginUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userService.login(user.getEmail(), user.getPassword());
        if (userOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("message", "Login successful!");
            return "redirect:/welcome";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error login. Wrong email or password.");
            return "login";
        }
    }
}

