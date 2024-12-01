package com.aimane.aimaneblog.Controller;

import com.aimane.aimaneblog.Model.Post;
import com.aimane.aimaneblog.Service.PostService;
import com.aimane.aimaneblog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class AdminController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public AdminController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    // Display admin dashboard for creating posts (make sure to secure this route)
    @GetMapping("/admin/dashboard") // Corrected to match the redirection
    public String showAdminDashboard(Model model) {

        return "admindashboard"; // Admin dashboard page
    }


}
