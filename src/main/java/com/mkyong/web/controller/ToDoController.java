package com.mkyong.web.controller;

import com.mkyong.web.model.ToDo;
import com.mkyong.web.model.User;
import com.mkyong.web.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/create")
    public String createToDo (HttpSession session,Model model){
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            model.addAttribute("error", "You must log in first.");
            return "login";
        }
            model.addAttribute("todo",new ToDo());
        return "todo-create";
    }

    @PostMapping("/create")
    public String saveToDo(@ModelAttribute("todo") ToDo toDo,
                           HttpSession session,
                           Model model) {

        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            model.addAttribute("error", "You must log in first.");
            return "login";
        }
        try {
            toDo.setUserId(currentUser.getId());
            toDoService.createToDo(toDo);
            model.addAttribute("message", "ToDo successfully created!");
            return "redirect:/todo/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error creating ToDo: " + e.getMessage());
            return "todo-create";
        }
    }

    @GetMapping("edit/update/{id}")
    public String showUpdateToDoForm(@PathVariable("id") Long id,HttpSession session,Model model){
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            model.addAttribute("error", "You must log in first.");
            return "login";
        }
        if(toDoService.getToDoById(id).isEmpty()){
            model.addAttribute("error", "Error ToDo update");
            return "todo-list";
        }
        ToDo toDo = toDoService.getToDoById(id).get();
        if(toDo.getUserId().equals(currentUser.getId())){
            model.addAttribute("todo", toDo);
            return "todo-edit";
        }else {
            model.addAttribute("error", "You can't change this ToDo");
            return "todo-list";
        }
    }

    @PostMapping("edit/update")
    public String updateToDo ( @ModelAttribute("todo") ToDo toDo,
                               HttpSession session,
                               RedirectAttributes redirectAttributes){
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "You must log in first.");
            return "redirect:/login";
        }
        if(toDo.getUserId().equals(currentUser.getId())){
            toDoService.updateToDo(toDo);
            redirectAttributes.addFlashAttribute("message", "ToDo updated successfully!");
            return "redirect:/todo/list";
        } else {
            redirectAttributes.addFlashAttribute("error", "You can't change this ToDo");
            return "redirect:/todo/list";
        }

    }


}
