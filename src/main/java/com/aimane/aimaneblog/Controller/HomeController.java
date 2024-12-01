package com.aimane.aimaneblog.Controller;

import com.aimane.aimaneblog.Model.Post;
import com.aimane.aimaneblog.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final PostService postService;

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<Post> posts = postService.getLatestPosts();  // Retrieve the latest posts
        model.addAttribute("posts", posts);  // Add posts to the model
        model.addAttribute("language", "en");  // Set the language attribute
        return "home";  // Return home.html
    }
}