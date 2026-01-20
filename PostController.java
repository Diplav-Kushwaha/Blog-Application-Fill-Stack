package com.diplav.blog.controller;

import com.diplav.blog.entity.Post;
import com.diplav.blog.entity.User;
import com.diplav.blog.repository.UserRepository;
import com.diplav.blog.service.CategoryService;
import com.diplav.blog.service.PostService;
import com.diplav.blog.service.TagService;
import com.diplav.blog.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/api/v1/posts")
public class PostController {

    private final UserRepository userRepository;
    private final PostService postService;
    private final TagService tagService;
    private final CategoryService categoryService;
    public PostController(PostService postService,
                          TagService tagService,
                          CategoryService categoryService,
                          UserService userService, UserRepository userRepository) {
        this.postService = postService;
        this.tagService = tagService;
        this.categoryService = categoryService;
        this.userRepository = userRepository;
    }

    @GetMapping("/myPosts")
    public String myPosts(Model model, Authentication authentication) {

        String email = authentication.getName();
        model.addAttribute("posts", postService.getMyPosts(email));
        return "my-posts";
    }

    @GetMapping("/newPost")
    public String newPostForm(Model model) {

        model.addAttribute("post", new Post());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("tags", tagService.getAllTags());

        return "new-post-form";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post,
                             @RequestParam("category.id") Long categoryId,
                             @RequestParam(value = "tags", required = false) List<Long> tagIds,
                             Authentication authentication){

        String email = authentication.getName();
        postService.createPost(post, categoryId, tagIds, email);
        return "redirect:/api/v1/posts/allPosts";
    }

    @GetMapping("/allPosts")
    public String getAllPosts(Model model, Authentication authentication){
        model.addAttribute("posts", postService.getAllPosts());
        model.addAttribute("post", new Post());

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            userRepository.findByEmail(email)
                    .ifPresent(user -> model.addAttribute("loggedInUser", user));
        }

        return "home-posts";
    }
    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model){
        model.addAttribute("post", postService.getPostById(id));
        return "home-post";
    }
    @RequestMapping("/deletePost/{id}")
    public String deletePost(@PathVariable Long id, Authentication authentication) {

        String email = authentication.getName();
        postService.deletePost(id, email);
        return "redirect:/api/v1/posts/allPosts";
    }
}
