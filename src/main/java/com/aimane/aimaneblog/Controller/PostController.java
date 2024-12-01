package com.aimane.aimaneblog.Controller;

import com.aimane.aimaneblog.Model.Post;
import com.aimane.aimaneblog.Model.User;
import com.aimane.aimaneblog.Service.PostService;
import com.aimane.aimaneblog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    // Get all posts (can be changed to home page if needed)
    @GetMapping("/home")  // Mapping to /home so posts are shown on home page
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "home"; // Make sure you are rendering posts in home.html
    }

    // Get a post by ID
    @GetMapping("/{id}")
    public String getPostById(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        post.ifPresent(p -> model.addAttribute("post", p));
        return "post-detail"; // This will show the post's details
    }

    // Show create post page (only accessible by admins)
    @GetMapping("/new")
    public String showCreatePostPage(Model model) {
        return "create-post"; // Page for creating new posts
    }

    // Create a new post (only allowed for admins)
    @PostMapping("/admin/posts")  // Corrected to map to /admin/posts
    public String createPost(@RequestParam String title, @RequestParam String content, @RequestParam MultipartFile postImg, @RequestParam Long userId, Model model) {
        try {
            // Check if the user is an admin
            if (userService.isAdmin(userId)) {
                User user = userService.getUserById(userId).orElse(null);
                if (user != null) {
                    // Create and save the post
                    Post post = new Post();
                    post.setTitle(title);
                    post.setContent(content);
                    post.setUser(user);
                    post.setCreatedDate(LocalDateTime.now());

                    // Handle image upload if available
                    if (postImg != null && !postImg.isEmpty()) {
                        try {
                            post.setPostImg(postImg.getBytes());
                        } catch (IOException e) {
                            model.addAttribute("error", "Error uploading the image. Please try again.");
                            return "admindashboard";
                        }
                    }

                    postService.savePost(post);
                    // Redirect to the admin dashboard after saving the post
                    return "redirect:/admin/dashboard";
                } else {
                    model.addAttribute("error", "User not found.");
                    return "admindashboard";
                }
            } else {
                model.addAttribute("error", "You must be an admin to create a post.");
                return "admindashboard";
            }
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred while creating the post. Please try again.");
            return "admindashboard";
        }
    }
}



