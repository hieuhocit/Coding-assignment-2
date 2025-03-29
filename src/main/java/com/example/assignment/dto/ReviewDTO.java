package com.example.assignment.dto;

public class ReviewDTO {
  private Integer id;
  private String title;
  private String author;
  private String content;
  private Integer bookId;

  public ReviewDTO(Integer id, String title, String author, String content, Integer bookId) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.bookId = bookId;
    this.content = content;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getBookId() {
    return bookId;
  }

  public void setBookId(Integer bookId) {
    this.bookId = bookId;
  }
}
