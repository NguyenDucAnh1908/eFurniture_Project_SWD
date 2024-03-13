package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.dtos.FeedbackDto;
import com.eFurnitureproject.eFurniture.models.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByProductId(Long productId);

    Page<Feedback> findAll(Specification<Feedback> spec, Pageable pageable);

    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.product.id = :productId")
    Double findAverageRatingByProductId(@Param("productId") Long productId);
    @Query("SELECT f.rating, COUNT(DISTINCT f.user.id) " +
            "FROM Feedback f " +
            "WHERE f.rating BETWEEN 1 AND 5 " +
            "GROUP BY f.rating")
    List<Object[]> findFeedbackCountByRating();

    @Query("SELECT NEW  com.eFurnitureproject.eFurniture.dtos.FeedbackDto(f.id, f.user.fullName, f.comment, f.parentId)" +
            "FROM Feedback as f WHERE f.parentId = :parent_id")
     List<FeedbackDto> findByParent(@Param("parent_id") Long parent_id);


    Page<Feedback> findAllByUserId(Pageable pageable, Long userId);
}
