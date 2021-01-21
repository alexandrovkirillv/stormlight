package com.secretNet.secNet.controllers;

import com.secretNet.secNet.models.UpdatePostTable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/updatePost")
    public String updatePost(Model model) throws SQLException, ClassNotFoundException {
        UpdatePostTable.updatePost();
        return "home";
    }

}