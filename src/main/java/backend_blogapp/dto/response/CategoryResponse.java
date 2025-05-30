package backend_blogapp.dto.response;

import lombok.*;

@Data@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private String slug;


}
