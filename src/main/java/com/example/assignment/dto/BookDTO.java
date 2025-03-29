package com.example.assignment.dto;

public class BookDTO {
  private Integer id;
  private String title;
  private String author;
  private Integer authorId;

  public BookDTO(Integer id, String title, String author, Integer authorId) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.authorId = authorId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Integer getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Integer authorId) {
    this.authorId = authorId;
  }

}
