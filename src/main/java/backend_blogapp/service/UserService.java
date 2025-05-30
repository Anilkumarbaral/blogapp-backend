package backend_blogapp.service;

import backend_blogapp.dto.request.UserUpdateRequest;
import backend_blogapp.dto.response.UserResponse;
import backend_blogapp.exception.ResourceNotFoundException;
import backend_blogapp.mapper.UserConverter;
import backend_blogapp.model.User;
import backend_blogapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final ModelMapper modelMapper;

    public UserService(ModelMapper modelMapper, UserConverter userConverter) {
        this.modelMapper = modelMapper;

        // Register custom converter once in constructor
        this.modelMapper.addConverter(userConverter,User.class,UserResponse.class);
    }
    @Autowired
    private UserRepository userRepository;

    // Get user profile
    public UserResponse getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToUserResponse(user);
    }



    // Update user profile
    public UserResponse updateUserProfile(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBio(request.getBio());
        user.setProfileImage(request.getProfileImage());

        User updatedUser = userRepository.save(user);
        return mapToUserResponse(updatedUser);
    }

    // Get user by username
    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToUserResponse(user);
    }
    private UserResponse mapToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
