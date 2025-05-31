package backend_blogapp.controller;

import backend_blogapp.dto.request.PostCreateRequest;
import backend_blogapp.dto.request.PostUpdateRequest;
import backend_blogapp.dto.response.PagedResponse;
import backend_blogapp.dto.response.PostResponse;
import backend_blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin // Adjust the origin as needed
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create a post
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostCreateRequest request,
                                                   @RequestParam Long authorId) {
        PostResponse post = postService.createPost(request, authorId);
        return ResponseEntity.ok(post);
    }

    // Get all published posts
    @GetMapping("/published")
    public ResponseEntity<PagedResponse<PostResponse>> getAllPublishedPosts(@RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.getAllPublishedPosts(page, size));
    }

    // Get post by ID
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Update post
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id,
                                                   @RequestBody PostUpdateRequest request,
                                                   @RequestParam Long authorId) {
        return ResponseEntity.ok(postService.updatePost(id, request, authorId));
    }

    // Delete post
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                        @RequestParam Long authorId) {
        postService.deletePost(id, authorId);
        return ResponseEntity.noContent().build();
    }

    // Publish post
    @PostMapping("/{id}/publish")
    public ResponseEntity<PostResponse> publishPost(@PathVariable Long id,
                                                    @RequestParam Long authorId) {
        return ResponseEntity.ok(postService.publishPost(id, authorId));
    }

    // Get user's posts
    @GetMapping("/user/{userId}")
    public ResponseEntity<PagedResponse<PostResponse>> getUserPosts(@PathVariable Long userId,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.getUserPosts(userId, page, size));
    }

    // Search published posts
    @GetMapping("/search")
    public ResponseEntity<PagedResponse<PostResponse>> searchPosts(@RequestParam String query,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.searchPosts(query, page, size));
    }
}

