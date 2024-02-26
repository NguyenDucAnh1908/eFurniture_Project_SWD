package com.eFurnitureproject.eFurniture.services;

import com.eFurnitureproject.eFurniture.models.TagsBlog;

import java.util.List;

public interface ITagsBlogService {
    List<TagsBlog> getAllTags();
    TagsBlog getTagById(Long id);
    TagsBlog saveTag(TagsBlog tag);
    void deleteTag(Long id);
}
