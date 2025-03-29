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

import com.example.assignment.models.Book;
import com.example.assignment.services.AuthorService;
import com.example.assignment.services.BookService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class BookController {
  @Autowired
  private BookService bookService;
  @Autowired
  private AuthorService authorService;

  @GetMapping("/books")
  public String list(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
    var booksPage = bookService.getBooksWithAuthor(page - 1, 5);

    model.addAttribute("books", booksPage.getContent()); // Lấy danh sách phần tử cho trang hiện tại
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", booksPage.getTotalPages()); // Tổng số trang

    return "views/books/list";
  }

  @GetMapping("/api/books")
  public ResponseEntity<?> list(@RequestParam(name = "page", defaultValue = "1") int page) {
    var books = bookService.findAll();
    return ResponseEntity.ok(books);
  }

  @GetMapping("/books/create")
  public String create(Model model) {
    var authors = authorService.findAll();

    model.addAttribute("authors", authors);

    return "views/books/create";
  }

  @PostMapping("/books")
  public ResponseEntity<?> create(@RequestBody Map<String, String> requestBody) {
    try {
      var newBook = new Book();

      var author = authorService.findById(Integer.parseInt(requestBody.get("authorId")));

      if (author == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Tác giả không tồn tại");
      }

      newBook.setTitle(requestBody.get("title"));
      newBook.setAuthor(author);

      var book = bookService.save(newBook);
      return ResponseEntity.status(HttpStatus.CREATED).body(book);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Không thể tạo sách: " + e.getMessage());
    }
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
    try {
      bookService.delete(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Không thể xóa sách: " + e.getMessage());
    }
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<?> update(@PathVariable(name = "id") String id,
      @RequestBody Map<String, String> requestBody) {

    try {
      var book = bookService.findById(Integer.parseInt(id));

      if (book == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Sách không tồn tại");
      }

      var author = authorService.findById(Integer.parseInt(requestBody.get("authorId")));

      if (author == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Tác giả không tồn tại");
      }

      book.setTitle(requestBody.get("title"));
      book.setAuthor(author);

      bookService.save(book);

      return ResponseEntity.ok().body("Sách đã được cập nhật thành công");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Không thể cập nhật sách: " + e.getMessage());
    }
  }
}
