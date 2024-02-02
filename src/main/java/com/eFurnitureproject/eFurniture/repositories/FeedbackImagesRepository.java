package com.eFurnitureproject.eFurniture.repositories;

import com.eFurnitureproject.eFurniture.models.FeedbackImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackImagesRepository extends JpaRepository<FeedbackImages, Long> {
}
