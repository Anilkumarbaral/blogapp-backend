package backend_blogapp.dto.response;

import backend_blogapp.model.enums.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Data@Setter@Getter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private String profileImage;
    private Set<Role> roles;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

