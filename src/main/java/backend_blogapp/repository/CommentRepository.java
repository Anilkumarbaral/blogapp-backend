package backend_blogapp.repository;

import backend_blogapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
    List<Comment> findByAuthorIdOrderByCreatedAtDesc(Long authorId);
    Long countByPostId(Long postId);
}
