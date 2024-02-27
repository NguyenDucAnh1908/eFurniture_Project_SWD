package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    boolean existsByTitle(String title);
    Page<Blog> findAll(Pageable pageable);
    Optional<Blog> findByIdAndActive(Long id, boolean active);


    @Query("SELECT b FROM Blog b " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR b.title LIKE %:keyword% OR b.content LIKE %:keyword%) " +
            "AND (:userBlogId IS NULL OR :userBlogId = 0 OR b.user.id = :userBlogId) ")
    Page<Blog> searchBlogs(
            @Param("keyword") String keyword, Pageable pageable,
            @Param("userBlogId") Long userBlogId);

}