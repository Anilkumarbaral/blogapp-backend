package backend_blogapp.service;

import backend_blogapp.model.Comment;
import backend_blogapp.model.Post;
import backend_blogapp.model.User;
import backend_blogapp.repository.CommentRepository;
import backend_blogapp.repository.PostRepository;
import backend_blogapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // Add comment to post
    public Comment addComment(Long postId, Long authorId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found!"));

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setAuthor(author);

        return commentRepository.save(comment);
    }

    // Get comments for a post
    public List<Comment> getPostCommentsuser(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
    }

    // Update comment
    public Comment updateComment(Long commentId, Long authorId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found!"));

        if (!comment.getAuthor().getId().equals(authorId)) {
            throw new RuntimeException("You can only edit your own comments!");
        }

        comment.setContent(content);
        return commentRepository.save(comment);
    }

    // Delete comment
    public void deleteCommentuser(Long commentId, Long authorId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found!"));

        if (!comment.getAuthor().getId().equals(authorId)) {
            throw new RuntimeException("You can only delete your own comments!");
        }

        commentRepository.delete(comment);
    }

    // Get user's comments
    public List<Comment> getUserComments(Long userId) {
        return commentRepository.findByAuthorIdOrderByCreatedAtDesc(userId);
    }

    // Count comments for a post
    public Long getCommentCountuser(Long postId) {
        return commentRepository.countByPostId(postId);
    }
}
