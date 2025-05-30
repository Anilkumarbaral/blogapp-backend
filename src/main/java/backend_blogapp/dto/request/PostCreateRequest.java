package backend_blogapp.dto.request;

import lombok.*;

@Data@Setter@Getter@AllArgsConstructor@NoArgsConstructor
public class PostCreateRequest {

    private String title;
    private String excerpt;
    private String content;
    private String featuredImage;
    private Long categoryId;

}
