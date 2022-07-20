package com.secretNet.secNet.controllers;

import com.secretNet.secNet.models.Post;
import com.secretNet.secNet.models.User;
import com.secretNet.secNet.repo.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

import static com.secretNet.secNet.models.Post.timeZone;
import static com.secretNet.secNet.models.Post.DATE_FORMAT;


@Controller
public class BlogController {

    public static final Logger LOG = LoggerFactory.getLogger(BlogController.class);
    public static final String REDIRECT_BLOG = "redirect:/blog";
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(@AuthenticationPrincipal User user, Model model) {
        Iterable<Post> posts = postRepository.findAll()
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        if (user != null) {
            model.addAttribute("user", "Bonjour, " + user.getUsername());
        }
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, String anons, String full_text, Model model,
                              @AuthenticationPrincipal User user) {
        Post post = new Post(title, anons, full_text, user.getUsername());
        postRepository.save(post);
        return REDIRECT_BLOG;
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
                             String anons, String full_text, Model model,
                             @AuthenticationPrincipal User user) {

        Post post = postRepository.findById(id).orElseThrow();
        post.setAnons(anons);
        post.setFull_text(full_text);
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timeZone));
        post.setTime(DATE_FORMAT.format(new Date()));
        post.setTitle(title);
        post.setUserName(user.getUsername());
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
