    package com.eFurnitureproject.eFurniture.controllers;

    import com.eFurnitureproject.eFurniture.Responses.BlogListResponse;
    import com.eFurnitureproject.eFurniture.Responses.BlogResponse;
    import com.eFurnitureproject.eFurniture.converter.BlogConverter;
    import com.eFurnitureproject.eFurniture.dtos.BlogDto;
    import com.eFurnitureproject.eFurniture.models.Blog;
    import com.eFurnitureproject.eFurniture.repositories.BlogRepository;
    import com.eFurnitureproject.eFurniture.services.impl.BlogService;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("api/blogs")
    @RequiredArgsConstructor
    public class BlogController {
        private final BlogService blogService;
        private final BlogRepository blogRepository;

        @GetMapping("/get_all_blogs")
        public ResponseEntity<BlogListResponse> getAllBlogs(
                @RequestParam(value = "keyword", required = false) String keyword,
                @RequestParam(value = "page", defaultValue = "0") int page,
                @RequestParam(value = "size", defaultValue = "10") int size) {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<BlogResponse> blogsPage = blogService.getAllBlogs(keyword, pageRequest, null, null);
            int totalPages = blogsPage.getTotalPages();
            List<BlogResponse> blogList = blogsPage.getContent();
            return ResponseEntity.ok(BlogListResponse.builder()
                    .blogs(blogList)
                    .totalPages(totalPages)
                    .build());
        }

        @PostMapping("/create_blog")
        public ResponseEntity<BlogResponse> createBlog(@RequestBody @Valid BlogDto blogDto) {
            Blog createdBlog = blogService.createBlog(blogDto);
            BlogResponse blogResponse = BlogConverter.toResponse(createdBlog);
            return ResponseEntity.ok(blogResponse);
        }



    }
