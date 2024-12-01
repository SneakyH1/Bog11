package com.aimane.aimaneblog.Controller;

import com.aimane.aimaneblog.Model.User;
import com.aimane.aimaneblog.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Handle login logic
// Handle login logic
    @PostMapping("/login")
    public String loginUser(String email, String password, Model model, Principal principal) {
        try {
            User user = userService.getUserByEmail(email).orElse(null);

            if (user != null && user.getPassword().equals(password)) {
                // Check user role
                if ("ROLE_ADMIN".equals(user.getRole())) {
                    // Admin user, redirect to dashboard
                    return "redirect:/admin/dashboard";
                } else {
                    // Regular user, redirect to home
                    return "redirect:/home";
                }
            } else {
                // Invalid login
                model.addAttribute("error", "Invalid email or password");
                return "login";  // Show login page with error
            }
        } catch (Exception e) {
            // Log the exception and return an error message
            e.printStackTrace();
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "login";  // Show the login page with a general error message
        }
    }




    // Show register page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // Handle registration logic
    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        // Set default role to ROLE_USER if it's not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
        }

        // Check if the user already exists
        if (userService.getUserByEmail(user.getEmail()).isEmpty()) {
            userService.saveUser(user);  // Save new user
            return "redirect:/home";  // Redirect to home page after registration
        } else {
            // If user already exists, show an error message
            model.addAttribute("error", "Email already in use");
            return "register";  // Show the register page again with an error
        }
    }
}

