package backend_blogapp.controller;


import backend_blogapp.dto.request.AuthRequest;
import backend_blogapp.dto.request.RegisterRequest;
import backend_blogapp.dto.response.AuthResponse;
import backend_blogapp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody AuthRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        System.out.println("Registering user: " + registerRequest.getUsername());
        return authService.registerUser(registerRequest);
    }
}