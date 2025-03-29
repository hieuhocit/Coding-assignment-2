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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.assignment.models.Author;
import com.example.assignment.services.AuthorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class AuthorController {
  @Autowired
  private AuthorService authorService;

  @GetMapping("/")
  public String list(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
    var authorsPage = authorService.getAuthorsWithBookCount(page - 1, 5);

    model.addAttribute("authors", authorsPage.getContent()); // Lấy danh sách phần tử cho trang hiện tại
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", authorsPage.getTotalPages()); // Tổng số trang

    return "views/authors/list";
  }

  @GetMapping("/api/authors")
  public ResponseEntity<?> listAuthor(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
    var authors = authorService.findAll();
    return ResponseEntity.ok().body(authors);
  }

  @GetMapping("/create")
  public String create() {
    return "views/authors/create";
  }

  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody Map<String, String> requestBody) {
    try {
      var newAuthor = new Author();
      newAuthor.setName(requestBody.get("name")); // Sử dụng phương thức getter từ DTO
      var author = authorService.save(newAuthor);
      return ResponseEntity.status(HttpStatus.CREATED).body(author);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Không thể tạo tác giả: " + e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
    try {

      authorService.delete(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Không thể xóa tác giả: " + e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateAuthor(@PathVariable(name = "id") String id,
      @RequestBody Map<String, String> requestBody) {

    try {
      var author = authorService.findById(Integer.parseInt(id));

      if (author == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Tác giả không tồn tại");
      }

      author.setName(requestBody.get("name"));
      authorService.save(author);

      return ResponseEntity.ok().body("Tác giả đã được cập nhật thành công");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Không thể cập nhật tác giả: " + e.getMessage());
    }
  }
}
