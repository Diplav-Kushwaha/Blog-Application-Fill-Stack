package com.diplav.blog.service;

import com.diplav.blog.entity.Tag;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();
    Tag createTag(Tag tag);
    void deleteTag(Long id);
    @Nullable Object getAll();
}
