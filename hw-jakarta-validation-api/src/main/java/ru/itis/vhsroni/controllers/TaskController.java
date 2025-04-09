package ru.itis.vhsroni.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.vhsroni.dto.TaskDto;


@Controller
@RequestMapping("/task")
public class TaskController {

    @GetMapping("/create")
    public String showForm(Model model) {
        model.addAttribute("taskDto", new TaskDto());
        return "task";
    }

    @PostMapping("/create")
    public String submitForm(@Valid @ModelAttribute("taskDto")TaskDto taskDto) {
        return "redirect:/task/result";
    }

    @GetMapping(value = "/result")
    public String showSuccessPage(Model model) {
        model.addAttribute("message", "Успех!!!");
        return "success";
    }
}

