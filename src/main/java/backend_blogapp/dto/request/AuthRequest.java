package backend_blogapp.dto.request;

import lombok.*;

@Data@Setter@Getter @NoArgsConstructor@AllArgsConstructor
@Builder
public class AuthRequest {
    private String username;
    private String password;
}
