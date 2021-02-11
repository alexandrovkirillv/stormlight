package com.secretNet.secNet.controllers;

import com.secretNet.secNet.models.RunPost;
import com.secretNet.secNet.models.User;
import com.secretNet.secNet.repo.RunRep;
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
public class RunController {

    public static final String REDIRECT_RUNNING = "redirect:/running";
    @Autowired
    private RunRep runRep;

    @GetMapping("/running")
    public String blogMain(@AuthenticationPrincipal User user, Model model) {
        Iterable<RunPost> posts = runRep.findAll()
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        if (user != null) {
            model.addAttribute("user", "Bonjour, " + user.getUsername());
        }
        model.addAttribute("posts", posts);
        return "running";
    }

    @GetMapping("/running/add")
    public String blogAdd(Model model) {
        return "run-add";
    }

    @PostMapping("/running/add")
    public String blogPostAdd(@RequestParam Integer trainNumber, String runTime, String ofpTime, Model model,
                              String pulse, String comment, @AuthenticationPrincipal User user) {
        RunPost runPost = new RunPost(trainNumber, runTime, ofpTime, pulse, user.getUsername(), comment);
        runRep.save(runPost);
        return REDIRECT_RUNNING;
    }

    private boolean getRunPost(@PathVariable("id") long id, Model model) {
        if (!runRep.existsById(id)) {
            return true;
        }
        Optional<RunPost> optionalPost = runRep.findById(id);
        List<RunPost> post = new ArrayList<>();
        optionalPost.ifPresent(post::add);
        model.addAttribute("post", post);
        return false;
    }

    @GetMapping("/running/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (getRunPost(id, model)) {
            return REDIRECT_RUNNING;
        }
        return "run-edit";
    }

    @PostMapping("/running/{id}/edit")
    public String blogUpdate(@PathVariable(value = "id") long id, @RequestParam Integer trainNumber,
                             String runTime, String ofpTime, Model model, String comment,
                             String pulse, @AuthenticationPrincipal User user) {

        RunPost post = runRep.findById(id).orElseThrow();
        post.setTrainNumber(trainNumber);
        post.setRunTime(runTime);
        post.setOfpTime(ofpTime);
        post.setPulse(pulse);
        post.setComment(comment);
        post.setTime(DATE_FORMAT.format(new Date()));
        post.setUserName(user.getUsername());
        runRep.save(post);
        return REDIRECT_RUNNING;
    }

    @PostMapping("/running/{id}/remove")
    public String blogDelete(@PathVariable(value = "id") long id, Model model) {
        RunPost post = runRep.findById(id).orElseThrow();
        runRep.delete(post);
        return REDIRECT_RUNNING;
    }

}
