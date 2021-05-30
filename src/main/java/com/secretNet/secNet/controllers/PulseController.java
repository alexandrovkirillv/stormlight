package com.secretNet.secNet.controllers;

import com.secretNet.secNet.models.Pulse;
import com.secretNet.secNet.models.User;
import com.secretNet.secNet.repo.PulseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

import static com.secretNet.secNet.models.Post.DATE_FORMAT;


@Controller
public class PulseController {

    public static final String REDIRECT_MEASURING = "redirect:/measuring";
    @Autowired
    private PulseRepo pulseRepo;

    @GetMapping("/measuring")
    public String blogMain(@AuthenticationPrincipal User user, Model model) {
        Iterable<Pulse> posts = pulseRepo.findAll()
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        if (user != null) {
            model.addAttribute("user", "Вы зашли под пользователем: " + user.getUsername());
        }
        model.addAttribute("posts", posts);
        return "measuring";
    }

    @GetMapping("/measuring/add")
    public String blogAdd(Model model) {
        return "measuring-add";
    }

    @PostMapping("/measuring/add")
    public String blogPostAdd(@RequestParam Integer restPulse, Integer wakeUpPulse, Integer firstMinutePulse,
                              Integer secondMinutePulse, Integer thirdMinutePulse,
                              String comment, @AuthenticationPrincipal User user) {
        Pulse pulse = new Pulse(restPulse, wakeUpPulse, firstMinutePulse, secondMinutePulse, thirdMinutePulse,
                comment, user.getUsername());
        pulseRepo.save(pulse);
        return REDIRECT_MEASURING;
    }

    private boolean getPulsePost(@PathVariable("id") long id, Model model) {
        if (!pulseRepo.existsById(id)) {
            return true;
        }
        Optional<Pulse> optionalPost = pulseRepo.findById(id);
        List<Pulse> post = new ArrayList<>();
        optionalPost.ifPresent(post::add);
        model.addAttribute("post", post);
        return false;
    }

    @GetMapping("/measuring/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (getPulsePost(id, model)) {
            return REDIRECT_MEASURING;
        }
        return "measuring-edit";
    }

    @PostMapping("/measuring/{id}/edit")
    public String blogUpdate(@PathVariable(value = "id") long id, @RequestParam Integer restPulse, Integer wakeUpPulse,
                            Integer firstMinutePulse, Integer secondMinutePulse, Integer thirdMinutePulse,
                             String comment, @AuthenticationPrincipal User user) {

        Pulse post = pulseRepo.findById(id).orElseThrow();
        post.setRestPulse(restPulse);
        post.setWakeUpPulse(wakeUpPulse);
        post.setFirstMinutePulse(firstMinutePulse);
        post.setSecondMinutePulse(secondMinutePulse);
        post.setThirdMinutePulse(thirdMinutePulse);
        post.setComment(comment);
        post.setTime(DATE_FORMAT.format(new Date()));
        post.setUserName(user.getUsername());
        pulseRepo.save(post);
        return REDIRECT_MEASURING;
    }

    @PostMapping("/measuring/{id}/remove")
    public String blogDelete(@PathVariable(value = "id") long id, Model model) {
        Pulse post = pulseRepo.findById(id).orElseThrow();
        pulseRepo.delete(post);
        return REDIRECT_MEASURING;
    }

}
