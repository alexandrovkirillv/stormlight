package com.secretNet.secNet.controllers;

import com.secretNet.secNet.models.Post;
import com.secretNet.secNet.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class BlogController {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss z");

    public static final String REDIRECT_BLOG = "redirect:/blog";
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository
                .findAll()
                .stream()
                .sorted()
                .collect(Collectors.toList());
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, String anons, String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return REDIRECT_BLOG;
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if (getPost(id, model)) {
            return REDIRECT_BLOG;
        }
        return "blog-details";
    }

    private boolean getPost(@PathVariable("id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return true;
        }
        Optional<Post> optionalPost = postRepository.findById(id);
        List<Post> post = new ArrayList<>();
        optionalPost.ifPresent(post::add);
        model.addAttribute("post", post);
        return false;
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (getPost(id, model)) {
            return REDIRECT_BLOG;
        }
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogUpdate(@PathVariable(value = "id") long id, @RequestParam String title,
                             String anons, String full_text, Model model) {

        Post post = postRepository.findById(id).orElseThrow();
        post.setAnons(anons);
        post.setFull_text(full_text);
        post.setTime(DATE_FORMAT.format(new Date()));
        post.setTitle(title);
        postRepository.save(post);
        return REDIRECT_BLOG;
    }

    @PostMapping("/blog/{id}/remove")
    public String blogDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return REDIRECT_BLOG;
    }

}
