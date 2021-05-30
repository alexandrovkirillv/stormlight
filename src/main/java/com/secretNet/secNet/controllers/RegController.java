package com.secretNet.secNet.controllers;

import com.secretNet.secNet.models.RegistrationForm;
import com.secretNet.secNet.models.User;
import com.secretNet.secNet.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String processUser(RegistrationForm registrationForm, Model model) {
        User user = userRepo.findByUsername(registrationForm.getUsername());
        if (user != null) {
            model.addAttribute("user", user.getUsername() + ", already exist!");
            return "registration";
        }
        userRepo.save(registrationForm.toUser(passwordEncoder));
        return "redirect:/login";
    }

}