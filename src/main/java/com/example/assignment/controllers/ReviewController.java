package com.example.assignment.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.assignment.models.Review;
import com.example.assignment.services.BookService;
import com.example.assignment.services.ReviewService;

@Controller
public class ReviewController {
  @Autowired
  private ReviewService reviewService;
  @Autowired
  private BookService bookService;

  @GetMapping("/reviews")
  public String list(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
    var reviewsPage = reviewService.getReviewsWithBookAndAuthor(page - 1, 5);

    model.addAttribute("reviews", reviewsPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", reviewsPage.getTotalPages());

    return "views/reviews/list";
  }

  @GetMapping("/reviews/create")
  public String create(Model model) {
    var books = bookService.findAll();

    model.addAttribute("books", books);

    return "views/reviews/create";
  }

  @PostMapping("/reviews")
  public ResponseEntity<?> create(@RequestBody Map<String, String> requestBody) {
    try {
      var newReview = new Review();

      var book = bookService.findById(Integer.parseInt(requestBody.get("bookId")));

      if (book == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Sách không tồn tại");
      }

      newReview.setContent(requestBody.get("content"));
      newReview.setBook(book);

      var review = reviewService.save(newReview);
      return ResponseEntity.status(HttpStatus.CREATED).body(review);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Không thể tạo đánh giá: " + e.getMessage());
    }
  }

  @DeleteMapping("/reviews/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
    try {
      reviewService.delete(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Không thể xóa sách: " + e.getMessage());
    }
  }

  @PutMapping("reviews/{id}")
  public ResponseEntity<?> update(@PathVariable(name = "id") String id,
      @RequestBody Map<String, String> requestBody) {

    try {
      var review = reviewService.findById(Integer.parseInt(id));

      if (review == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Đánh giá không tồn tại");
      }

      review.setContent(requestBody.get("content"));

      reviewService.save(review);

      return ResponseEntity.ok().body("Đánh giá đã được cập nhật thành công");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Không thể cập nhật đánh giá: " + e.getMessage());
    }
  }
}
