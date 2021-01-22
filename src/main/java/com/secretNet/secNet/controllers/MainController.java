package com.secretNet.secNet.controllers;

import com.secretNet.secNet.models.UpdatePostColumn;
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

    @GetMapping("/updatepost")
    public String updatePost(Model model) throws SQLException, ClassNotFoundException {
        UpdatePostColumn.updatePost();
        return "home";
    }

}