package backend_blogapp.controller;


import backend_blogapp.dto.request.UserUpdateRequest;
import backend_blogapp.dto.response.UserResponse;
import backend_blogapp.model.User;
import backend_blogapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get profile of the authenticated user
     */
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserProfile(Authentication authentication) {
        Long userId = extractUserIdFromAuthentication(authentication); // custom method
        UserResponse response = userService.getUserProfile(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Update profile of the authenticated user
     */
    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUserProfile(
            Authentication authentication,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        Long userId = extractUserIdFromAuthentication(authentication);
        UserResponse response = userService.updateUserProfile(userId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get user by username (admin only or public)
     */
    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        UserResponse response = userService.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    /**
     * Example utility to extract user ID from authentication token
     * (you can modify this according to your JWT setup)
     */
    private Long extractUserIdFromAuthentication(Authentication authentication) {
        // You could use Principal or custom UserDetails implementation
        User userDetails = (User) authentication.getPrincipal();
        return userDetails.getId(); // assuming your userDetails has getId()
    }
}

