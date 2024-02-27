package com.eFurnitureproject.eFurniture.services.impl;

import com.eFurnitureproject.eFurniture.models.TagsBlog;
import com.eFurnitureproject.eFurniture.repositories.TagsBlogRepository;
import com.eFurnitureproject.eFurniture.services.ITagsBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagsBlogService implements ITagsBlogService {
    private final TagsBlogRepository tagsBlogRepository;
    @Override
    public List<TagsBlog> getAllTags() {
        return tagsBlogRepository.findAll();
    }

    @Override
    public TagsBlog getTagById(Long id) {
        return tagsBlogRepository.findById(id).orElse(null);
    }

    @Override
    public TagsBlog saveTag(TagsBlog tag) {
        return tagsBlogRepository.save(tag);
    }

    @Override
    public void deleteTag(Long id) {
        tagsBlogRepository.deleteById(id);
    }
}
