package com.example.assignment.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.assignment.dto.BookDTO;
import com.example.assignment.models.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
  @Query("SELECT new com.example.assignment.dto.BookDTO(b.id, b.title, a.name, a.id) " +
      "FROM Book b JOIN Author a ON a.id = b.author.id " +
      "ORDER BY b.createdAt DESC")
  Page<BookDTO> findBooksWithAuthor(Pageable pageable);
}
