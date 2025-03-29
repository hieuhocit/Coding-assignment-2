package com.example.assignment.dto;

public class AuthorDTO {
  private Integer id;
  private String name;
  private Long bookCount;

  public AuthorDTO(Integer id, String name, Long bookCount) {
    this.id = id;
    this.name = name;
    this.bookCount = bookCount;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Long getBookCount() {
    return bookCount;
  }
}
