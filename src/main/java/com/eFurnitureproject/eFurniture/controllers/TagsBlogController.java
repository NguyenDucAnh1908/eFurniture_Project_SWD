package com.eFurnitureproject.eFurniture.controllers;


import com.eFurnitureproject.eFurniture.models.TagsBlog;
import com.eFurnitureproject.eFurniture.services.impl.TagsBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tags-blog")
@RequiredArgsConstructor
public class TagsBlogController {

    private final TagsBlogService tagsBlogService;

    @GetMapping("/get-all")
    public ResponseEntity<List<TagsBlog>> getAllTags() {
        List<TagsBlog> tags = tagsBlogService.getAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<TagsBlog> getTagById(@PathVariable Long id) {
        TagsBlog tag = tagsBlogService.getTagById(id);
        if (tag != null) {
            return new ResponseEntity<>(tag, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TagsBlog> createTag(@RequestBody TagsBlog tag) {
        TagsBlog savedTag = tagsBlogService.saveTag(tag);
        return new ResponseEntity<>(savedTag, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagsBlog> updateTag(@PathVariable Long id, @RequestBody TagsBlog tagDetails) {
        TagsBlog existingTag = tagsBlogService.getTagById(id);
        if (existingTag != null) {
            existingTag.setTagName(tagDetails.getTagName());
            // Cập nhật các trường khác cần thiết nếu có
            TagsBlog updatedTag = tagsBlogService.saveTag(existingTag);
            return new ResponseEntity<>(updatedTag, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagsBlogService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}