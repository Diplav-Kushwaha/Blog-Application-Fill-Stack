package com.diplav.blog.controller;

import com.diplav.blog.entity.Category;
import com.diplav.blog.entity.Tag;
import com.diplav.blog.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/api/v1/tags")
public class TagController {

    private final TagService tagService;
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/allTags")
    public String getAllTags(Model model){
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("tag", new Tag());
        return "home-tags";
    }
    @GetMapping("/newTag")
    public String newTagForm(Model model) {
        model.addAttribute("tag", new Tag());
        return "new-tag-form";
    }
    @PostMapping("/createTags")
    public String createTags(@ModelAttribute Tag tags){
        tagService.createTag(tags);
        return "redirect:/api/v1/tags/allTags";
    }
    @RequestMapping("/deleteTag/{id}")
    public String deleteTag(@PathVariable Long id){
        tagService.deleteTag(id);
        return "redirect:/api/v1/tags/allTags";
    }
}
