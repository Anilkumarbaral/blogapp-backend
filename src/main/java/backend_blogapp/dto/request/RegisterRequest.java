package backend_blogapp.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String email;
    private String password;

    // Optional roles like ROLE_USER or ROLE_ADMIN
    private Set<String> roles;
}

