package com.mkyong.web.controller;

import com.mkyong.web.model.ToDo;
import com.mkyong.web.model.User;
import com.mkyong.web.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @GetMapping("/list")
    public String getUserToDoList(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            model.addAttribute("error", "You must log in first.");
            return "login";
        }
        try {
            List<ToDo> todos = toDoService.getToDosByUser(currentUser);
            model.addAttribute("todos", todos);
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving todos " + e.getMessage());
        }
        return "todo-list";
    }
}
