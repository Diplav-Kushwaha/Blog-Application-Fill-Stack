package com.diplav.blog.repository;

import com.diplav.blog.entity.Post;
import com.diplav.blog.entity.User;
import com.diplav.blog.enumeration.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByStatus(PostStatus status);
    Optional<Post> findByIdAndStatus(Long id, PostStatus postStatus);
    boolean existsByTitle(String title);
    List<Post> findAllByAuthorAndStatus(User author, PostStatus status);
}
