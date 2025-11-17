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
import java.util.Optional;

@Controller
@RequestMapping("/todo")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    private User getCurrentUser(HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "You must log in first.");
        }
        return user;
    }

    @GetMapping("/list")
    public String getUserToDoList(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser(session, redirectAttributes);
        if (currentUser == null) {
            return "redirect:/login";
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
    public String createToDo(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser(session, redirectAttributes);
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("todo", new ToDo());
        return "todo-create";
    }

    @PostMapping("/create")
    public String saveToDo(@ModelAttribute("todo") ToDo toDo,
                           HttpSession session,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser(session, redirectAttributes);
        if (currentUser == null) {
            return "redirect:/login";
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
    public String showUpdateToDoForm(@PathVariable("id") Long id,
                                     HttpSession session,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser(session, redirectAttributes);
        if (currentUser == null) {
            return "redirect:/login";
        }
        if (toDoService.getToDoById(id).isEmpty()) {
            model.addAttribute("error", "Error ToDo update");
            return "todo-list";
        }
        ToDo toDo = toDoService.getToDoById(id).get();
        if (toDo.getUserId().equals(currentUser.getId())) {
            model.addAttribute("todo", toDo);
            return "todo-edit";
        } else {
            model.addAttribute("error", "You can't change this ToDo");
            return "todo-list";
        }
    }

    @PostMapping("edit/update")
    public String updateToDo(@ModelAttribute("todo") ToDo toDo,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser(session, redirectAttributes);
        if (currentUser == null) {
            return "redirect:/login";
        }
        if (toDo.getUserId().equals(currentUser.getId())) {
            toDoService.updateToDo(toDo);
            redirectAttributes.addFlashAttribute("message", "ToDo updated successfully!");
            return "redirect:/todo/list";
        } else {
            redirectAttributes.addFlashAttribute("error", "You can't change this ToDo");
            return "redirect:/todo/list";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteToDo(@PathVariable("id") Long id,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser(session, redirectAttributes);
        if (currentUser == null) {
            return "redirect:/login";
        }
        if(id<=0){
            redirectAttributes.addFlashAttribute("error", "Invalid ToDo ID."+id);
            return "redirect:/todo/list";
        }
        Optional<ToDo> optionalToDo = toDoService.getToDoById(id);
        if (optionalToDo.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "ToDo not found!");
            return "redirect:/todo/list";
        }
        ToDo todoFromDb = optionalToDo.get();
        if (todoFromDb.getUserId().equals(currentUser.getId())) {
            toDoService.deleteToDo(id);
            redirectAttributes.addFlashAttribute("success", "ToDo delete successfully!");
            return "redirect:/todo/list";
        } else {
            redirectAttributes.addFlashAttribute("error", "You cannot delete a ToDo that does not belong to you.");
            return "redirect:/todo/list";
        }
    }


}
