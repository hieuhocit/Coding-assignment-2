package com.example.assignment.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.assignment.dto.AuthorDTO;
import com.example.assignment.models.Author;
import com.example.assignment.repositories.AuthorRepository;

@Service
public class AuthorService {
  @Autowired
  private AuthorRepository authorRepository;

  public Page<AuthorDTO> getAuthorsWithBookCount(int page, int size) {
    return authorRepository.findAuthorsWithBookCount(PageRequest.of(page, size));
  }

  public List<Author> findAll() {
    return authorRepository.findAll();
  }

  public Author save(Author author) {
    return authorRepository.save(author);
  }

  public Author findById(Integer id) {
    return authorRepository.findById(id).orElse(null);
  }

  public void delete(Integer id) {
    authorRepository.deleteById(id);
  }
}
