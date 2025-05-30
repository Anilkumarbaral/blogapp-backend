package backend_blogapp.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Setter @Getter
public class PostUpdateRequest {

    private String title;
    private String excerpt;
    private String content;
    private String featuredImage;
    private Long categoryId;
}
