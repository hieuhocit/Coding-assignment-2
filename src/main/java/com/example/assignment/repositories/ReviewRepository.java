package com.example.assignment.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.assignment.dto.ReviewDTO;
import com.example.assignment.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
  @Query("SELECT new com.example.assignment.dto.ReviewDTO(r.id, b.title, a.name,r.content ,b.id)" +
      "FROM Review r JOIN Book b ON b.id = r.book.id " +
      "JOIN Author a ON a.id = b.author.id " +
      "ORDER BY r.createdAt DESC")
  Page<ReviewDTO> findReviewsWithBookAndAuthor(Pageable pageable);
}
