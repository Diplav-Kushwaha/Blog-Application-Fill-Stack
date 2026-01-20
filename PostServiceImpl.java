package com.diplav.blog.serviceImpl;

import com.diplav.blog.entity.Category;
import com.diplav.blog.entity.Post;
import com.diplav.blog.entity.Tag;
import com.diplav.blog.entity.User;
import com.diplav.blog.enumeration.PostStatus;
import com.diplav.blog.repository.CategoryRepository;
import com.diplav.blog.repository.PostRepository;
import com.diplav.blog.repository.TagRepository;
import com.diplav.blog.repository.UserRepository;
import com.diplav.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    public PostServiceImpl(UserRepository userRepository,
                           PostRepository postRepository,
                           CategoryRepository categoryRepository,
                           TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findByIdAndStatus(id, PostStatus.PUBLISHED)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

    }

    @Override
    public Post createPost(Post post, Long categoryId, List<Long> tagIds, String email) {

        if (postRepository.existsByTitle(post.getTitle())) {
            throw new IllegalArgumentException("Title already exists");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Set<Tag> tags = new HashSet<>();
        if (tagIds != null && !tagIds.isEmpty()) {
            tags.addAll(tagRepository.findAllById(tagIds));
        }

        post.setAuthor(user);
        post.setCategory(category);
        post.setTags(tags);
        post.setStatus(PostStatus.PUBLISHED);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId, String userEmail) {

        User loggedInUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getAuthor().getId().equals(loggedInUser.getId())) {
            throw new SecurityException("You are not allowed to delete this post");
        }
        postRepository.delete(post);
    }

    @Override
    public List<Post> getMyPosts(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        return postRepository.findAllByAuthorAndStatus(user, PostStatus.PUBLISHED);
    }
}
