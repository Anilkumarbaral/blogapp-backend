package backend_blogapp.service;


import backend_blogapp.dto.request.AuthRequest;
import backend_blogapp.dto.request.RegisterRequest;
import backend_blogapp.dto.response.AuthResponse;
import backend_blogapp.jwt.JwtService;
import backend_blogapp.model.User;
import backend_blogapp.model.enums.Role;
import backend_blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {


    @Autowired(required = true)
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    // Add these fields to the class
    @Autowired
    private PasswordEncoder passwordEncoder;


    // Add this method to the class
    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        Set<String> strRoles = registerRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            roles.add(Role.USER);
        } else {
            for (String roleStr : strRoles) {
                roles.add(Role.valueOf(roleStr.toUpperCase())); // Be careful of invalid inputs!
            }
        }


//        User user = User.builder()
//                .username(registerRequest.getUsername())
//
//                .password(passwordEncoder.encode(registerRequest.getPassword()))
//                .email()
//                .roles(roles)
//                .build();
        User user = User.builder().username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .roles(roles)
                .build();

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }


    public AuthResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        String jwt = jwtService.generateToken(user);

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return AuthResponse.builder()
                .token(jwt)
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }
}