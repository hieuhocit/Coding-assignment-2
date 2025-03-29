package com.example.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.assignment.dto.ReviewDTO;
import com.example.assignment.models.Review;
import com.example.assignment.repositories.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {
  @Autowired
  private ReviewRepository reviewRepository;

  public Page<ReviewDTO> getReviewsWithBookAndAuthor(int page, int size) {
    return reviewRepository.findReviewsWithBookAndAuthor(PageRequest.of(page, size));
  }

  public List<Review> findAll() {
    return reviewRepository.findAll();
  }

  public Review findById(Integer id) {
    return reviewRepository.findById(id).orElse(null);
  }

  public Review save(Review review) {
    return reviewRepository.save(review);
  }

  public void delete(Integer id) {
    reviewRepository.deleteById(id);
  }
}
