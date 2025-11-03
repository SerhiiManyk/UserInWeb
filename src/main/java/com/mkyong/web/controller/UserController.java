package com.mkyong.web.controller;

import com.mkyong.web.model.User;
import org.springframework.ui.Model;
import com.mkyong.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("user",new User());
        return "create-user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, Model model){
        try{
            userService.createUser(user);
            model.addAttribute("message", "User successfully created!");
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", "Error creating user: " + e.getMessage());
            return "create-user";
        }
    }
}
