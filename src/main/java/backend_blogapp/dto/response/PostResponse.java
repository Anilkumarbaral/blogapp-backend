package backend_blogapp.dto.response;

import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String excerpt;
    private String content;
    private String featuredImage;
    private String authorName;
    private String categoryName;
}