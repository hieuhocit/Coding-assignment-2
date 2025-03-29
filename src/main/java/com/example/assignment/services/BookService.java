package com.example.assignment.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.assignment.dto.BookDTO;
import com.example.assignment.models.Book;
import com.example.assignment.repositories.BookRepository;

@Service
public class BookService {
  @Autowired
  private BookRepository bookRepository;

  public Page<BookDTO> getBooksWithAuthor(int page, int size) {
    return bookRepository.findBooksWithAuthor(PageRequest.of(page, size));
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Book findById(Integer id) {
    return bookRepository.findById(id).orElse(null);
  }

  public Book save(Book book) {
    return bookRepository.save(book);
  }

  public void delete(Integer id) {
    bookRepository.deleteById(id);
  }
}
