package backend_blogapp.service;

import backend_blogapp.dto.request.PostCreateRequest;
import backend_blogapp.dto.request.PostUpdateRequest;
import backend_blogapp.dto.response.PagedResponse;
import backend_blogapp.dto.response.PostResponse;
import backend_blogapp.exception.ResourceNotFoundException;
import backend_blogapp.exception.UnauthorizedException;
import backend_blogapp.mapper.PostConverter;
import backend_blogapp.model.Category;
import backend_blogapp.model.Post;
import backend_blogapp.model.User;
import backend_blogapp.model.enums.PostStatus;
import backend_blogapp.repository.CategoryRepository;
import backend_blogapp.repository.PostRepository;
import backend_blogapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Service
@Transactional
public class PostService {

    private final ModelMapper modelMapper;
    private final PostConverter postConverter;

    public PostService(ModelMapper modelMapper, PostConverter postConverter) {
        this.modelMapper = modelMapper;
        this.postConverter = postConverter;

        // Register custom converter once in constructor
        this.modelMapper.addConverter(postConverter);
    }

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;



    // Create new post
    public PostResponse createPost(PostCreateRequest request, Long authorId) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setExcerpt(request.getExcerpt());
        post.setContent(request.getContent());
        post.setFeaturedImage(request.getFeaturedImage());
        post.setAuthor(author);

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            post.setCategory(category);
        }
        post.setStatus(PostStatus.PUBLISHED);
        post.setPublishedAt(LocalDateTime.now());
        Post savedPost = postRepository.save(post);
        return mapToPostResponse(savedPost);
    }

    // Get all published posts (paginated)
    public PagedResponse<PostResponse> getAllPublishedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> posts = postRepository.findByStatus(PostStatus.PUBLISHED, pageable);

        return new PagedResponse<>(
                posts.getContent().stream().map(this::mapToPostResponse).collect(Collectors.toList()),
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast()
        );
    }

    // Get post by ID
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return mapToPostResponse(post);
    }

    // Update post
    public PostResponse updatePost(Long id, PostUpdateRequest request, Long authorId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        // Check if user is the author
        if (!post.getAuthor().getId().equals(authorId)) {
            throw new UnauthorizedException("You can only edit your own posts");
        }

        post.setTitle(request.getTitle());
        post.setExcerpt(request.getExcerpt());
        post.setContent(request.getContent());
        post.setFeaturedImage(request.getFeaturedImage());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            post.setCategory(category);
        }

        Post updatedPost = postRepository.save(post);
        return mapToPostResponse(updatedPost);
    }

    // Delete post
    public void deletePost(Long id, Long authorId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (!post.getAuthor().getId().equals(authorId)) {
            throw new UnauthorizedException("You can only delete your own posts");
        }

        postRepository.delete(post);
    }

    // Publish post
    public PostResponse publishPost(Long id, Long authorId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (!post.getAuthor().getId().equals(authorId)) {
            throw new UnauthorizedException("You can only publish your own posts");
        }

        post.setStatus(PostStatus.PUBLISHED);
        post.setPublishedAt(LocalDateTime.now());

        Post publishedPost = postRepository.save(post);
        return mapToPostResponse(publishedPost);
    }

    // Get user's posts
    public PagedResponse<PostResponse> getUserPosts(Long userId, int page, int size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> posts = postRepository.findByAuthor(user, pageable);

        return new PagedResponse<>(
                posts.getContent().stream().map(this::mapToPostResponse).collect(Collectors.toList()),
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast()
        );
    }

    // Search posts
    public PagedResponse<PostResponse> searchPosts(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> posts = postRepository.findByStatusAndTitleContainingIgnoreCase(
                PostStatus.PUBLISHED, query, pageable);

        return new PagedResponse<>(
                posts.getContent().stream().map(this::mapToPostResponse).collect(Collectors.toList()),
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast()
        );
    }
    private PostResponse mapToPostResponse(Post post) {
        return modelMapper.map(post, PostResponse.class);
    }

}
