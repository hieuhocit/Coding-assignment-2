package com.example.assignment.repositories;

import com.example.assignment.dto.AuthorDTO;
import com.example.assignment.models.Author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
  @Query("SELECT new com.example.assignment.dto.AuthorDTO(a.id, a.name, COUNT(b.id)) " +
      "FROM Author a LEFT JOIN Book b ON a.id = b.author.id " +
      "GROUP BY a.id, a.name " +
      "ORDER BY a.createdAt DESC")
  Page<AuthorDTO> findAuthorsWithBookCount(Pageable pageable);
}
