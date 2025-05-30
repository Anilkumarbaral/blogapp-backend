package backend_blogapp.dto.response;

import lombok.*;

import java.util.List;

@Data@Setter@Getter@NoArgsConstructor@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
