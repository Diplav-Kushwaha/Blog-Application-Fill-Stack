package com.diplav.blog.serviceImpl;

import com.diplav.blog.entity.Category;
import com.diplav.blog.entity.Tag;
import com.diplav.blog.repository.TagRepository;
import com.diplav.blog.service.TagService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        List<Tag> tags=tagRepository.findAll();
        return tags;
    }

    @Override
    public Tag createTag(Tag tag) {
        if (tagRepository.existsByName(tag.getName())) {
            throw new IllegalArgumentException("Tag already exists");
        }
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Tag not found"));

        if (!tag.getPosts().isEmpty()) {
            throw new IllegalStateException("Tag is associated with posts");
        }
        tagRepository.delete(tag);
    }
    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }
}
