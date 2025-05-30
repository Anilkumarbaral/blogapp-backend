package backend_blogapp.dto.request;

import lombok.*;

@Data @Setter@Getter@NoArgsConstructor@AllArgsConstructor
public class CategoryRequest {
    private String name;
    private String description;

}
