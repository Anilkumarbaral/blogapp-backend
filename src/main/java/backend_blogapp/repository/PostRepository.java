package backend_blogapp.repository;

import backend_blogapp.model.Category;
import backend_blogapp.model.Post;
import backend_blogapp.model.User;
import backend_blogapp.model.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByStatus(PostStatus status, Pageable pageable);
    Page<Post> findByAuthor(User author, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);
    Page<Post> findByStatusAndTitleContainingIgnoreCase(PostStatus status, String title, Pageable pageable);
    List<Post> findTop5ByStatusOrderByCreatedAtDesc(PostStatus status);
}
