package backend_blogapp.mapper;

import backend_blogapp.dto.response.PostResponse;
import backend_blogapp.model.Post;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class PostConverter implements Converter<Post, PostResponse> {

    @Override
    public PostResponse convert(MappingContext<Post, PostResponse> context) {
        Post post = context.getSource();
        PostResponse response = new PostResponse();

        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setExcerpt(post.getExcerpt());
        response.setContent(post.getContent());
        response.setFeaturedImage(post.getFeaturedImage());
//        response.setCreatedAt(post.getCreatedAt());
//        response.setUpdatedAt(post.getUpdatedAt());

        if (post.getAuthor() != null) {
            response.setAuthorName(post.getAuthor().getUsername());
        }
        if (post.getCategory() != null) {
            response.setCategoryName(post.getCategory().getName());
        }
        return response;
    }
}

