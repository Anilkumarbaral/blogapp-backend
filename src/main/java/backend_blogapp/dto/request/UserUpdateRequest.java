package backend_blogapp.dto.request;


import lombok.Data;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String bio;
    private String profileImage;
}
