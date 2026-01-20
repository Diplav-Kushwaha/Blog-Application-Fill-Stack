package com.diplav.blog.service;

import com.diplav.blog.entity.Post;
import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(Long id);
    Post createPost(Post post, Long categoryId, List<Long> tagIds, String email);
    void deletePost(Long id, String email);
    List<Post> getMyPosts(String email);
}
